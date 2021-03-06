package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.ILLEGAL_TOOLBAR_ACTION
import br.com.mathsemilio.simpleapodbrowser.common.event.ToolbarActionClickEvent
import br.com.mathsemilio.simpleapodbrowser.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.common.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.OperationResult
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.FetchAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren

class APoDListScreen : BaseFragment(),
    APoDListContract.View.Listener,
    APoDListContract.Screen,
    EventPoster.EventListener,
    FetchAPoDUseCase.Listener {

    private lateinit var view: APoDListScreenView

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var eventPoster: EventPoster

    private lateinit var fetchAPoDUseCase: FetchAPoDUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screensNavigator = compositionRoot.screensNavigator
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        eventPoster = compositionRoot.eventPoster
        fetchAPoDUseCase = compositionRoot.fetchAPoDUseCase
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getApodListScreenView(container)
        return view.rootView
    }

    override fun onApodClicked(apod: APoD) {
        screensNavigator.navigateToAPoDDetailsScreen(apod, apod.mediaType)
    }

    override fun onScreenSwipedToRefresh() {
        fetchApods()
    }

    override fun fetchApods() {
        // TODO Fetch APoDs
    }

    override fun onFetchApodsStarted() {
        view.showProgressIndicator()
    }

    override fun onFetchApodsCompleted(apods: List<APoD>) {
        view.hideNetworkRequestFailedState()
        view.hideProgressIndicator()
        view.onRefreshCompleted()
        view.bindApods(apods)
    }

    override fun onFetchApodsFailed(errorCode: String) {
        view.hideProgressIndicator()
        view.showNetworkRequestErrorState(errorCode)
    }

    override fun onToolbarActionPickApodByDateClicked() {
        // TODO Show Date Picker Dialog
    }

    override fun onToolbarActionGetRandomAPoDClicked() {
        // TODO Fetch random APoD and launch Details Screen
    }

    override fun onToolbarActionVisitApodWebsiteClicked() {
        launchWebPage(requireContext(), getString(R.string.apod_website_url))
    }

    override fun handleToolbarActionClickEvent(action: ToolbarAction) {
        when (action) {
            ToolbarAction.PICK_APOD_BY_DATE -> onToolbarActionPickApodByDateClicked()
            ToolbarAction.GET_RANDOM_APOD -> onToolbarActionGetRandomAPoDClicked()
            ToolbarAction.VISIT_APOD_WEBSITE -> onToolbarActionVisitApodWebsiteClicked()
            else -> throw IllegalArgumentException(ILLEGAL_TOOLBAR_ACTION)
        }
    }

    override fun onEvent(event: Any) {
        when (event) {
            is ToolbarActionClickEvent -> handleToolbarActionClickEvent(event.action)
        }
    }

    override fun onFetchAPodUseCaseEvent(result: OperationResult<List<APoD>>) {
        when (result) {
            OperationResult.OnStarted -> onFetchApodsStarted()
            is OperationResult.OnCompleted -> onFetchApodsCompleted(result.data!!)
            is OperationResult.OnError -> onFetchApodsFailed(result.errorMessage!!)
        }
    }

    override fun onStart() {
        view.addListener(this)
        eventPoster.addListener(this)
        fetchAPoDUseCase.addListener(this)
        fetchApods()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventPoster.removeListener(this)
        fetchAPoDUseCase.removeListener(this)
        super.onStop()
    }

    override fun onDestroy() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onDestroy()
    }
}