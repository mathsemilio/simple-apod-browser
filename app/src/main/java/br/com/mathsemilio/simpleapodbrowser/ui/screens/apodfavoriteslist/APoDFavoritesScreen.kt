package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.DeleteFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.APoDFavoritesScreenView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.APoDFavoritesScreenViewImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class APoDFavoritesScreen : BaseFragment(),
    APoDFavoritesScreenView.Listener,
    DeleteFavoriteAPoDUseCase.Listener,
    FetchFavoriteAPoDUseCase.Listener {

    private lateinit var view: APoDFavoritesScreenViewImpl

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope

    private lateinit var fetchFavoriteAPoDUseCase: FetchFavoriteAPoDUseCase
    private lateinit var deleteFavoriteAPoDUseCase: DeleteFavoriteAPoDUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screensNavigator = compositionRoot.screensNavigator
        messagesManager = compositionRoot.messagesManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        fetchFavoriteAPoDUseCase = compositionRoot.fetchFavoriteAPoDUseCase
        deleteFavoriteAPoDUseCase = compositionRoot.deleteFavoriteAPoDUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getAPoDFavoritesScreenView(container)
        return view.rootView
    }

    private fun fetchFavoriteAPoDs() {
        coroutineScope.launch {
            view.showProgressIndicator()
            fetchFavoriteAPoDUseCase.fetchFavoriteAPods()
        }
    }

    private fun revertFavoriteAPoDDeletion() {
        coroutineScope.launch {
            deleteFavoriteAPoDUseCase.revertFavoriteAPoDDeletion()
        }
    }

    override fun onFavoriteAPoDClicked(apod: APoD) {
        screensNavigator.toAPoDDetailsScreen(apod)
    }

    override fun onRemoveFromFavoritesIconClicked(apod: APoD) {
        coroutineScope.launch {
            deleteFavoriteAPoDUseCase.deleteFavoriteAPoD(apod)
        }
    }

    override fun onFavoriteAPoDDeletedSuccessfully() {
        messagesManager.showDeleteFavoriteAPoDUseCaseSuccessMessage(view.rootView) {
            revertFavoriteAPoDDeletion()
        }
    }

    override fun onFavoriteAPoDDeleteRevertedSuccessfully() {
        fetchFavoriteAPoDs()
    }

    override fun onDeleteFavoriteAPoDFailed() {
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onRevertFavoriteAPoDDeletionFailed() {
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onFetchFavoriteAPoDCompleted(favoriteApods: List<APoD>) {
        view.hideProgressIndicator()
        view.bindFavoriteApods(favoriteApods)
    }

    override fun onFetchFavoriteAPoDFailed() {
        view.hideProgressIndicator()
        messagesManager.showUnexpectedErrorOccurredMessage()
    }

    override fun onStart() {
        view.addListener(this)
        fetchFavoriteAPoDUseCase.addListener(this)
        deleteFavoriteAPoDUseCase.addListener(this)
        fetchFavoriteAPoDs()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        fetchFavoriteAPoDUseCase.removeListener(this)
        deleteFavoriteAPoDUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
        super.onStop()
    }
}