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

package br.com.mathsemilio.simpleapodbrowser.ui.screens.favoriteapods

import android.view.*
import android.os.Bundle
import kotlinx.coroutines.*
import androidx.appcompat.widget.SearchView
import br.com.mathsemilio.simpleapodbrowser.R
import androidx.navigation.fragment.findNavController
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.*
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.*
import br.com.mathsemilio.simpleapodbrowser.ui.screens.favoriteapods.view.*
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.util.onQueryTextChangedListener
import br.com.mathsemilio.simpleapodbrowser.ui.common.delegate.ContainerLayoutDelegate
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchFavoriteApodsUseCase.*
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchFavoriteApodsFromSearchQueryUseCase.*

class FavoriteApodsFragment : BaseFragment(), FavoriteApodsScreenView.Listener {

    private lateinit var view: FavoriteApodsScreenView

    private lateinit var containerLayoutDelegate: ContainerLayoutDelegate

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager

    private lateinit var fetchFavoriteApodUseCase: FetchFavoriteApodsUseCase
    private lateinit var fetchFavoriteApodsFromSearchQueryUseCase: FetchFavoriteApodsFromSearchQueryUseCase

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        containerLayoutDelegate = compositionRoot.containerLayoutDelegate

        screensNavigator = ScreensNavigator(findNavController())
        messagesManager = compositionRoot.messagesManager

        fetchFavoriteApodUseCase = compositionRoot.fetchFavoriteApodsUseCase
        fetchFavoriteApodsFromSearchQueryUseCase = compositionRoot.fetchFavoriteApodsFromSearchQueryUseCase
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

    private fun fetchFavoriteApods() {
        coroutineScope.launch {
            view.showProgressIndicator()
            handleFetchFavoriteApodsResult(fetchFavoriteApodUseCase.fetchFavoriteApods())
        }
    }

    private fun handleFetchFavoriteApodsResult(result: FetchFavoriteApodsResult) {
        when (result) {
            is FetchFavoriteApodsResult.Completed ->
                onFetchFavoriteApodsCompleted(result.favoriteApods)
            FetchFavoriteApodsResult.Failed ->
                onFetchFavoriteApodsFailed()
        }
    }

    private fun onFetchFavoriteApodsCompleted(favoriteApods: List<Apod>) {
        view.hideProgressIndicator()
        view.bind(favoriteApods)
    }

    private fun onFetchFavoriteApodsFailed() {
        view.hideProgressIndicator()
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_favorite_apods, menu)

        val searchFavoritesMenuItem = menu.findItem(R.id.toolbar_action_search_favorites)
        val searchFavoritesSearchView = searchFavoritesMenuItem.actionView as SearchView

        searchFavoritesSearchView.onQueryTextChangedListener { searchQuery ->
            fetchFavoriteApodsBasedOn(searchQuery)
        }
    }

    private fun fetchFavoriteApodsBasedOn(searchQuery: String) {
        coroutineScope.launch {
            handleFetchFavoriteApodsFromSearchQueryResult(
                fetchFavoriteApodsFromSearchQueryUseCase.fetchFrom(searchQuery)
            )
        }
    }

    private fun handleFetchFavoriteApodsFromSearchQueryResult(
        result: FetchFavoriteApodsFromSearchQueryResult
    ) {
        when (result) {
            is FetchFavoriteApodsFromSearchQueryResult.Completed ->
                onFetchFavoriteApodsFromSearchQueryCompleted(result.matchingApods)
            FetchFavoriteApodsFromSearchQueryResult.Failed ->
                view.hideProgressIndicator()
        }
    }

    private fun onFetchFavoriteApodsFromSearchQueryCompleted(matchingApods: List<Apod>) {
        view.hideProgressIndicator()
        view.bind(matchingApods)
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
        view.addObserver(this)
        fetchFavoriteApods()
    }

    override fun onStop() {
        super.onStop()
        view.removeObserver(this)
        coroutineScope.coroutineContext.cancelChildren()
    }
}
