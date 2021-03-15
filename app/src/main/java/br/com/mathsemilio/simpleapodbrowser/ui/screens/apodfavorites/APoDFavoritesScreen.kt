package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.ILLEGAL_TOOLBAR_ACTION
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.SearchViewEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.SnackBarEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.common.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.OperationResult
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.AddFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.DeleteFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.FetchFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
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
        coroutineScope.launch { deleteFavoriteAPoDUseCase.deleteFavoriteAPoD(favoriteApod) }
    }

    override fun fetchFavoriteApods() {
        coroutineScope.launch { fetchFavoriteAPoDUseCase.fetchFavoriteAPoDs() }
    }

    override fun onToolbarActionVisitApodWebsiteClicked() {
        launchWebPage(requireContext(), getString(R.string.apod_website_url))
    }

    override fun onToolbarSearchViewTextEntered(userInput: String) {
        coroutineScope.launch { fetchFavoriteAPoDUseCase.fetchFavoriteAPoDBasedOnName(userInput) }
    }

    override fun onSnackBarActionUndoClicked() {
        coroutineScope.launch { addFavoriteAPoDUseCase.addFavoriteAPoD(lastDeletedFavoriteAPoD) }
    }

    override fun handleToolbarSearchViewEvent(event: SearchViewEvent) {
        when (event) {
            is SearchViewEvent.TextEntered -> coroutineScope.launch {
                fetchFavoriteAPoDUseCase.fetchFavoriteAPoDBasedOnName(event.input)
            }
        }
    }

    override fun handleToolbarActionClickEvent(action: ToolbarAction) {
        when (action) {
            ToolbarAction.VISIT_APOD_WEBSITE -> onToolbarActionVisitApodWebsiteClicked()
            else -> throw IllegalArgumentException(ILLEGAL_TOOLBAR_ACTION)
        }
    }

    override fun handleSnackBarActionEvent(event: SnackBarEvent) {
        when (event) {
            SnackBarEvent.UndoClicked -> coroutineScope.launch {
                addFavoriteAPoDUseCase.addFavoriteAPoD(lastDeletedFavoriteAPoD)
            }
        }
    }

    override fun onAddFavoriteAPodUseCaseEvent(result: OperationResult<Nothing>) {
        when (result) {
            OperationResult.OnStarted -> onAddFavoriteAPoDCompleted()
            is OperationResult.OnCompleted -> onAddFavoriteAPoDCompleted()
            is OperationResult.OnError -> onAddFavoriteAPoDFailed(result.errorMessage!!)
        }
    }

    override fun onAddFavoriteAPoDStarted() {
        view.showProgressIndicator()
    }

    override fun onAddFavoriteAPoDCompleted() {
        view.hideProgressIndicator()
        fetchFavoriteApods()
    }

    override fun onAddFavoriteAPoDFailed(errorMessage: String) {
        view.hideProgressIndicator()
        messagesManager.showUseCaseErrorMessage(errorMessage)
    }

    override fun onFetchFavoriteAPodUseCaseEvent(result: OperationResult<List<FavoriteAPoD>>) {
        when (result) {
            OperationResult.OnStarted -> onFetchFavoriteApodsStarted()
            is OperationResult.OnCompleted -> onFetchFavoriteApodsCompleted(result.data!!)
            is OperationResult.OnError -> onFetchFavoriteApodsFailed(result.errorMessage!!)
        }
    }

    override fun onFetchFavoriteApodsStarted() {
        view.showProgressIndicator()
    }

    override fun onFetchFavoriteApodsCompleted(favoriteApods: List<FavoriteAPoD>) {
        view.hideProgressIndicator()
        view.bindFavoriteApods(favoriteApods)
    }

    override fun onFetchFavoriteApodsFailed(errorMessage: String) {
        view.hideProgressIndicator()
        messagesManager.showUseCaseErrorMessage(errorMessage)
    }

    override fun onDeleteFavoriteAPodUseCaseEvent(result: OperationResult<Nothing>) {
        when (result) {
            OperationResult.OnStarted -> onDeleteFavoriteApodStarted()
            is OperationResult.OnCompleted -> onDeleteFavoriteApodCompleted()
            is OperationResult.OnError -> onDeleteFavoriteApodFailed(result.errorMessage!!)
        }
    }

    override fun onDeleteFavoriteApodStarted() {
        view.showProgressIndicator()
    }

    override fun onDeleteFavoriteApodCompleted() {
        view.hideProgressIndicator()
        messagesManager.showDeleteFavoriteAPoDSuccessMessage(view.rootView)
    }

    override fun onDeleteFavoriteApodFailed(errorMessage: String) {
        view.hideProgressIndicator()
        messagesManager.showUseCaseErrorMessage(errorMessage)
    }

    override fun onEvent(event: Any) {
        when (event) {
            is SearchViewEvent -> handleToolbarSearchViewEvent(event)
            is ToolbarEvent -> handleToolbarActionClickEvent(event.action)
            is SnackBarEvent -> handleSnackBarActionEvent(event)
        }
    }

    override fun onStart() {
        view.addListener(this)
        eventPoster.addListener(this)
        fetchFavoriteAPoDUseCase.addListener(this)
        deleteFavoriteAPoDUseCase.addListener(this)
        fetchFavoriteApods()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventPoster.removeListener(this)
        fetchFavoriteAPoDUseCase.removeListener(this)
        deleteFavoriteAPoDUseCase.removeListener(this)
        super.onStop()
    }

    override fun onDestroy() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onDestroy()
    }
}