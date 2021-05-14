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
package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.util.onQueryTextChangedListener
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.DeleteFavoriteApodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchFavoriteApodUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.HostLayoutHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.SnackBarManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.ApodFavoritesScreenView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class ApodFavoritesFragment : BaseFragment(),
    ApodFavoritesScreenView.Listener,
    DeleteFavoriteApodUseCase.Listener,
    FetchFavoriteApodUseCase.Listener {

    private lateinit var view: ApodFavoritesScreenView

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var hostLayoutHelper: HostLayoutHelper
    private lateinit var messagesManager: MessagesManager
    private lateinit var snackBarManager: SnackBarManager
    private lateinit var coroutineScope: CoroutineScope

    private lateinit var fetchFavoriteApodUseCase: FetchFavoriteApodUseCase
    private lateinit var deleteFavoriteApodUseCase: DeleteFavoriteApodUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        screensNavigator = compositionRoot.screensNavigator
        hostLayoutHelper = compositionRoot.hostLayoutHelper
        messagesManager = compositionRoot.messagesManager
        snackBarManager = compositionRoot.snackBarManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        fetchFavoriteApodUseCase = compositionRoot.fetchFavoriteApodUseCase
        deleteFavoriteApodUseCase = compositionRoot.deleteFavoriteApodUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getApodFavoritesScreenView(container)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchFavoriteApods()
    }

    private fun fetchFavoriteApods() {
        coroutineScope.launch {
            view.showProgressIndicator()
            fetchFavoriteApodUseCase.getFavoriteApods()
        }
    }

    private fun revertFavoriteApodDeletion() {
        coroutineScope.launch {
            deleteFavoriteApodUseCase.revertFavoriteApodDeletion()
        }
    }

    override fun onFavoriteApodClicked(favoriteApod: Apod) {
        screensNavigator.toFavoriteApodDetailsScreen(favoriteApod)
    }

    override fun onFavoriteApodSwipedToDelete(favoriteApod: Apod) {
        coroutineScope.launch {
            deleteFavoriteApodUseCase.deleteFavoriteApod(favoriteApod)
        }
    }

    override fun onRemoveFromFavoritesIconClicked(apod: Apod) {
        coroutineScope.launch {
            deleteFavoriteApodUseCase.deleteFavoriteApod(apod)
            fetchFavoriteApods()
        }
    }

    override fun onDeleteFavoriteApodCompleted() {
        snackBarManager.showFavoriteApodDeletedSuccessfullySnackBar(
            hostLayoutHelper.getFragmentContainer(),
            hostLayoutHelper.getBottomNavigationView(),
            { revertFavoriteApodDeletion() },
            { fetchFavoriteApods() }
        )
    }

    override fun onRevertFavoriteApodDeletionCompleted() {
        fetchFavoriteApods()
    }

    override fun onDeleteFavoriteApodFailed() {
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onRevertFavoriteApodDeletionFailed() {
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onFetchFavoriteApodsCompleted(favoriteApods: List<Apod>) {
        view.hideProgressIndicator()
        view.bindFavoriteApods(favoriteApods)
    }

    override fun onFetchFavoriteApodsBasedOnSearchQueryCompleted(matchingApods: List<Apod>) {
        view.hideProgressIndicator()
        view.bindFavoriteApods(matchingApods)
    }

    override fun onFetchFavoriteApodFailed() {
        view.hideProgressIndicator()
        messagesManager.showUnexpectedErrorOccurredMessage()
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
            fetchFavoriteApodUseCase.getFavoriteApodsBasedOnSearchQuery(searchQuery)
        }
    }

    override fun onStart() {
        view.addListener(this)
        fetchFavoriteApodUseCase.addListener(this)
        deleteFavoriteApodUseCase.addListener(this)
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        fetchFavoriteApodUseCase.removeListener(this)
        deleteFavoriteApodUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
        super.onStop()
    }
}