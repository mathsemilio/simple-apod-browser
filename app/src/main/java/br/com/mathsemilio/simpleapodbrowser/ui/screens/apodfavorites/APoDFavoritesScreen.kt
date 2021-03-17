package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.ILLEGAL_TOOLBAR_ACTION
import br.com.mathsemilio.simpleapodbrowser.common.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.AddFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.DeleteFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.SnackBarEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class APoDFavoritesScreen : BaseFragment(),
    APoDFavoritesContract.View.Listener,
    APoDFavoritesContract.Screen,
    AddFavoriteAPoDUseCase.Listener,
    FetchFavoriteAPoDUseCase.Listener,
    DeleteFavoriteAPoDUseCase.Listener,
    EventPoster.EventListener {

    private lateinit var view: APoDFavoritesView

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var eventPoster: EventPoster

    private lateinit var addFavoriteAPoDUseCase: AddFavoriteAPoDUseCase
    private lateinit var fetchFavoriteAPoDUseCase: FetchFavoriteAPoDUseCase
    private lateinit var deleteFavoriteAPoDUseCase: DeleteFavoriteAPoDUseCase

    private lateinit var lastDeletedFavoriteAPoD: FavoriteAPoD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screensNavigator = compositionRoot.screensNavigator
        messagesManager = compositionRoot.messagesManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        eventPoster = compositionRoot.eventPoster
        addFavoriteAPoDUseCase = compositionRoot.addFavoriteAPoDUseCase
        fetchFavoriteAPoDUseCase = compositionRoot.fetchFavoriteAPoDUseCase
        deleteFavoriteAPoDUseCase = compositionRoot.deleteFavoriteAPoDUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getApodFavoritesView(container)
        return view.rootView
    }

    override fun onFavoriteAPoDClicked(favoriteApod: FavoriteAPoD) {
        screensNavigator.navigateToAPoDDetailsScreen(favoriteApod, favoriteApod.mediaType)
    }

    override fun onRemoveFavoriteAPoDIconClicked(favoriteApod: FavoriteAPoD) {
        lastDeletedFavoriteAPoD = favoriteApod
        coroutineScope.launch {
            view.showProgressIndicator()
            deleteFavoriteAPoDUseCase.deleteFavoriteAPoD(favoriteApod)
        }
    }

    override fun fetchFavoriteApods() {
        coroutineScope.launch {
            view.showProgressIndicator()
            fetchFavoriteAPoDUseCase.fetchFavoriteAPoDs()
        }
    }

    override fun onToolbarActionVisitApodWebsiteClicked() {
        launchWebPage(requireContext(), getString(R.string.apod_website_url))
    }

    override fun onToolbarActionClickEvent(action: ToolbarAction) {
        when (action) {
            ToolbarAction.VISIT_APOD_WEBSITE -> onToolbarActionVisitApodWebsiteClicked()
            else -> throw IllegalArgumentException(ILLEGAL_TOOLBAR_ACTION)
        }
    }

    override fun onSnackBarActionEvent(event: SnackBarEvent) {
        when (event) {
            SnackBarEvent.UndoClicked -> coroutineScope.launch {
                addFavoriteAPoDUseCase.reAddFavoriteAPoD(lastDeletedFavoriteAPoD)
            }
        }
    }

    override fun onFavoriteAPoDAddedSuccessfully() {
        fetchFavoriteApods()
    }

    override fun onFavoriteAPoDAddFailed(errorMessage: String) {
        messagesManager.showUseCaseErrorMessage(errorMessage)
    }

    override fun onFetchFavoriteAPoDCompleted(favoriteApods: List<FavoriteAPoD>) {
        view.hideProgressIndicator()
        view.bindFavoriteApods(favoriteApods)
    }

    override fun onFetchFavoriteAPoDFailed(errorMessage: String) {
        view.hideProgressIndicator()
        messagesManager.showUseCaseErrorMessage(errorMessage)
    }

    override fun onFavoriteAPoDDeletedSuccessfully() {
        view.hideProgressIndicator()
        messagesManager.showDeleteFavoriteAPoDSuccessMessage(view.rootView)
        fetchFavoriteApods()
    }

    override fun onFavoriteAPoDDeleteFailed(errorMessage: String) {
        view.hideProgressIndicator()
        messagesManager.showUseCaseErrorMessage(errorMessage)
    }

    override fun onEvent(event: Any) {
        when (event) {
            is ToolbarEvent -> onToolbarActionClickEvent(event.action)
            is SnackBarEvent -> onSnackBarActionEvent(event)
        }
    }

    override fun onStart() {
        view.addListener(this)
        eventPoster.addListener(this)
        addFavoriteAPoDUseCase.addListener(this)
        fetchFavoriteAPoDUseCase.addListener(this)
        deleteFavoriteAPoDUseCase.addListener(this)
        fetchFavoriteApods()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventPoster.removeListener(this)
        addFavoriteAPoDUseCase.removeListener(this)
        fetchFavoriteAPoDUseCase.removeListener(this)
        deleteFavoriteAPoDUseCase.removeListener(this)
        super.onStop()
    }

    override fun onDestroy() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onDestroy()
    }
}