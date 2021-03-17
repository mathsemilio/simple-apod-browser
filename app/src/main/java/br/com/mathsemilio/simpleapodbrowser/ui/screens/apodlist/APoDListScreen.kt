package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.ILLEGAL_TOOLBAR_ACTION
import br.com.mathsemilio.simpleapodbrowser.common.formatTimeInMillis
import br.com.mathsemilio.simpleapodbrowser.common.getWeekRangeDate
import br.com.mathsemilio.simpleapodbrowser.common.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.DateSetEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.DialogManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class APoDListScreen : BaseFragment(),
    APoDListContract.View.Listener,
    APoDListContract.Screen,
    FetchAPoDUseCase.Listener,
    EventPoster.EventListener {

    private lateinit var view: APoDListScreenView

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var dialogManager: DialogManager
    private lateinit var eventPoster: EventPoster

    private lateinit var fetchAPoDUseCase: FetchAPoDUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screensNavigator = compositionRoot.screensNavigator
        messagesManager = compositionRoot.messagesManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        dialogManager = compositionRoot.dialogManager
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
        coroutineScope.launch { fetchAPoDUseCase.fetchAPoDBasedOnDateRange(getWeekRangeDate()) }
    }

    override fun onToolbarActionPickApodByDateClicked() {
        dialogManager.showDatePickerDialog()
    }

    override fun onToolbarActionGetRandomAPoDClicked() {
        coroutineScope.launch { fetchAPoDUseCase.fetchRandomAPoD() }
    }

    override fun onToolbarActionVisitApodWebsiteClicked() {
        launchWebPage(requireContext(), getString(R.string.apod_website_url))
    }

    override fun onAPoDDatePicked(dateSet: Long) {
        coroutineScope.launch { fetchAPoDUseCase.fetchAPoDBasedOnDate(dateSet.formatTimeInMillis()) }
    }

    override fun onInvalidAPoDDatePicked() {
        messagesManager.showInvalidAPoDDateErrorMessage()
    }

    override fun onToolbarActionClickEvent(action: ToolbarAction) {
        when (action) {
            ToolbarAction.PICK_APOD_BY_DATE -> onToolbarActionPickApodByDateClicked()
            ToolbarAction.GET_RANDOM_APOD -> onToolbarActionGetRandomAPoDClicked()
            ToolbarAction.VISIT_APOD_WEBSITE -> onToolbarActionVisitApodWebsiteClicked()
            else -> throw IllegalArgumentException(ILLEGAL_TOOLBAR_ACTION)
        }
    }

    override fun onDateSetEvent(event: DateSetEvent) {
        when (event) {
            is DateSetEvent.DateSet -> coroutineScope.launch {
                fetchAPoDUseCase.fetchAPoDBasedOnDate(event.dateSetInMillis.formatTimeInMillis())
            }
            DateSetEvent.InvalidDateSet ->
                messagesManager.showInvalidAPoDDateErrorMessage()
        }
    }

    override fun onFetchAPoDsCompleted(apods: List<APoD>) {
        view.hideNetworkRequestFailedState()
        view.hideProgressIndicator()
        view.onRefreshCompleted()
        view.bindApods(apods)
    }

    override fun onFetchAPoDBasedOnDateCompleted(apod: APoD) {
        screensNavigator.navigateToAPoDDetailsScreen(apod, apod.mediaType)
    }

    override fun onFetchRandomAPoDCompleted(apod: List<APoD>) {
        screensNavigator.navigateToAPoDDetailsScreen(apod.first(), apod.first().mediaType)
    }

    override fun onFetchAPoDFailed(errorMessage: String) {
        view.hideProgressIndicator()
        view.showNetworkRequestErrorState(errorMessage)
    }

    override fun onEvent(event: Any) {
        when (event) {
            is ToolbarEvent -> onToolbarActionClickEvent(event.action)
            is DateSetEvent -> onDateSetEvent(event)
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

    override fun onDestroyView() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onDestroyView()
    }
}