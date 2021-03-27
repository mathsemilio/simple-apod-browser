package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.ILLEGAL_TOOLBAR_ACTION
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventListener
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventSubscriber
import br.com.mathsemilio.simpleapodbrowser.common.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.DeleteFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.APoDFavoritesScreenView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.APoDFavoritesScreenViewImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class APoDFavoritesScreen : BaseFragment(),
    APoDFavoritesScreenView.Listener,
    DeleteFavoriteAPoDUseCase.Listener,
    FetchFavoriteAPoDUseCase.Listener,
    EventListener {

    private lateinit var view: APoDFavoritesScreenViewImpl

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope

    private lateinit var eventSubscriber: EventSubscriber

    private lateinit var fetchFavoriteAPoDUseCase: FetchFavoriteAPoDUseCase
    private lateinit var deleteFavoriteAPoDUseCase: DeleteFavoriteAPoDUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screensNavigator = compositionRoot.screensNavigator
        messagesManager = compositionRoot.messagesManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        eventSubscriber = compositionRoot.eventSubscriber
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

    private fun onToolbarActionClicked(action: ToolbarAction) {
        when (action) {
            ToolbarAction.VISIT_APOD_WEBSITE -> requireContext().launchWebPage(
                getString(R.string.apod_website_url)
            )
            else -> throw IllegalArgumentException(ILLEGAL_TOOLBAR_ACTION)
        }
    }

    override fun onFavoriteAPoDClicked(apod: APoD) {
        screensNavigator.toAPoDDetailsScreen(apod, isFavoriteAPoD = true)
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

    override fun onEvent(event: Any) {
        when (event) {
            is ToolbarEvent.ActionClicked -> onToolbarActionClicked(event.action)
        }
    }

    override fun onStart() {
        view.addListener(this)
        eventSubscriber.subscribe(this)
        fetchFavoriteAPoDUseCase.addListener(this)
        deleteFavoriteAPoDUseCase.addListener(this)
        fetchFavoriteAPoDs()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventSubscriber.unsubscribe(this)
        fetchFavoriteAPoDUseCase.removeListener(this)
        deleteFavoriteAPoDUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
        super.onStop()
    }
}