/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.simpleapodbrowser.ui.screens.favoriteapodslist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.util.onQueryTextChangedListener
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.DeleteFavoriteApodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.DeleteFavoriteApodUseCase.DeleteFavoriteApodResult
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.DeleteFavoriteApodUseCase.RevertFavoriteApodDeletionResult
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchApodsBasedOnSearchQueryUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchApodsBasedOnSearchQueryUseCase.FetchApodBasedOnSearchQueryResult
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchFavoriteApodsUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchFavoriteApodsUseCase.FetchFavoriteApodsResult
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.delegate.ContainerLayoutDelegate
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.SnackBarManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.screens.favoriteapodslist.view.FavoriteApodsScreenView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.favoriteapodslist.view.FavoriteApodsScreenViewImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class FavoriteApodsFragment : BaseFragment(), FavoriteApodsScreenView.Listener {

    private lateinit var view: FavoriteApodsScreenView

    private lateinit var containerLayoutDelegate: ContainerLayoutDelegate

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager
    private lateinit var snackBarManager: SnackBarManager
    private lateinit var coroutineScope: CoroutineScope

    private lateinit var fetchFavoriteApodUseCase: FetchFavoriteApodsUseCase
    private lateinit var deleteFavoriteApodUseCase: DeleteFavoriteApodUseCase
    private lateinit var fetchApodsBasedOnSearchQueryUseCase: FetchApodsBasedOnSearchQueryUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        containerLayoutDelegate = compositionRoot.containerLayoutDelegate

        screensNavigator = ScreensNavigator(findNavController())
        messagesManager = compositionRoot.messagesManager
        snackBarManager = compositionRoot.snackBarManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope

        fetchFavoriteApodUseCase = compositionRoot.fetchFavoriteApodUseCase
        deleteFavoriteApodUseCase = compositionRoot.deleteFavoriteApodUseCase
        fetchApodsBasedOnSearchQueryUseCase = compositionRoot.fetchApodsBasedOnSearchQueryUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FavoriteApodsScreenViewImpl(inflater, container)
        return view.rootView
    }

    override fun onFavoriteApodClicked(favoriteApod: Apod) {
        screensNavigator.toFavoriteApodDetailsScreen(favoriteApod)
    }

    override fun onFavoriteApodSwipedToDelete(favoriteApod: Apod) {
        coroutineScope.launch {
            val result = deleteFavoriteApodUseCase.deleteFavoriteApod(favoriteApod)
            handleDeleteFavoriteApodResult(result)
        }
    }

    private fun handleDeleteFavoriteApodResult(result: DeleteFavoriteApodResult) {
        when (result) {
            is DeleteFavoriteApodResult.Completed ->
                showFavoriteApodDeletedSuccessfullySnackBar()
            DeleteFavoriteApodResult.Failed ->
                messagesManager.showUnexpectedErrorOccurredMessage()
        }
    }

    private fun showFavoriteApodDeletedSuccessfullySnackBar() {
        snackBarManager.showFavoriteApodDeletedSuccessfullySnackBar(
            view = view.rootView,
            containerLayoutDelegate.bottomNavigationView,
            onSnackBarActionClicked = { revertFavoriteApodDeletion() },
            onSnackBarTimedOut = { fetchFavoriteApods() }
        )
    }

    private fun revertFavoriteApodDeletion() {
        coroutineScope.launch {
            val result = deleteFavoriteApodUseCase.revertFavoriteApodDeletion()
            handleRevertFavoriteApodDeletionResult(result)
        }
    }

    private fun handleRevertFavoriteApodDeletionResult(result: RevertFavoriteApodDeletionResult) {
        when (result) {
            RevertFavoriteApodDeletionResult.Completed ->
                fetchFavoriteApods()
            RevertFavoriteApodDeletionResult.Failed ->
                messagesManager.showUnexpectedErrorOccurredMessage()
        }
    }

    private fun fetchFavoriteApods() {
        coroutineScope.launch {
            view.showProgressIndicator()
            val result = fetchFavoriteApodUseCase.fetchFavoriteApods()
            handleFetchFavoriteApodsResult(result)
        }
    }

    private fun handleFetchFavoriteApodsResult(result: FetchFavoriteApodsResult) {
        when (result) {
            is FetchFavoriteApodsResult.Completed -> {
                view.hideProgressIndicator()
                result.apods?.let { view.bind(it) }
            }
            FetchFavoriteApodsResult.Failed -> {
                view.hideProgressIndicator()
                messagesManager.showUnexpectedErrorOccurredMessage()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_apod_favorites, menu)

        val searchViewMenuItem = menu.findItem(R.id.toolbar_action_search_favorites)
        val searchView = searchViewMenuItem.actionView as SearchView

        searchView.onQueryTextChangedListener { searchQuery ->
            fetchFavoriteApodsBasedOnSearchQuery(searchQuery)
        }
    }

    private fun fetchFavoriteApodsBasedOnSearchQuery(searchQuery: String) {
        coroutineScope.launch {
            val result = fetchApodsBasedOnSearchQueryUseCase.fetchApodsBasedOn(searchQuery)
            handleFetchApodsBasedOnSearchQueryResult(result)
        }
    }

    private fun handleFetchApodsBasedOnSearchQueryResult(
        result: FetchApodBasedOnSearchQueryResult
    ) {
        when (result) {
            is FetchApodBasedOnSearchQueryResult.Completed -> {
                view.hideProgressIndicator()
                result.matchingApods?.let { view.bind(it) }
            }
            FetchApodBasedOnSearchQueryResult.Failed -> {
                view.hideProgressIndicator()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_action_settings -> {
                screensNavigator.toSettingsScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        view.addListener(this)
        fetchFavoriteApods()
    }

    override fun onStop() {
        super.onStop()
        view.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}
