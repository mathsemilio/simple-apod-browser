package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventListener
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventSubscriber
import br.com.mathsemilio.simpleapodbrowser.common.getLastSevenDays
import br.com.mathsemilio.simpleapodbrowser.common.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.FetchAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.DateSetEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarEvent
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
    EventListener {

    private lateinit var view: APoDListScreenView

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var eventSubscriber: EventSubscriber
    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var dialogManager: DialogManager

    private lateinit var fetchAPoDUseCase: FetchAPoDUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screensNavigator = compositionRoot.screensNavigator
        eventSubscriber = compositionRoot.eventSubscriber
        messagesManager = compositionRoot.messagesManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        dialogManager = compositionRoot.dialogManager
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchApods()
    }

    override fun onApodClicked(apod: APoD) {
        screensNavigator.toDetailsScreen(apod)
    }

    override fun onScreenSwipedToRefresh() {
        fetchApods()
    }

    override fun fetchApods() {
        coroutineScope.launch {
            view.showProgressIndicator()
            fetchAPoDUseCase.fetchAPoDBasedOnDateRange(getLastSevenDays())
        }
    }

    override fun onToolbarActionPickApodByDateClicked() {
        dialogManager.showDatePickerDialog()
    }

    override fun onToolbarActionGetRandomAPoDClicked() {
        coroutineScope.launch { fetchAPoDUseCase.fetchRandomAPoD() }
    }

    override fun onToolbarActionVisitApodWebsiteClicked() {
        requireContext().launchWebPage(getString(R.string.apod_website_url))
    }

    override fun onDateSetEvent(event: DateSetEvent) {
        when (event) {
            is DateSetEvent.DateSet -> coroutineScope.launch {
                fetchAPoDUseCase.fetchAPoDBasedOnDate(event.dateInMillis)
            }
            DateSetEvent.InvalidDateSet ->
                messagesManager.showInvalidAPoDDateErrorMessage()
        }
    }

    override fun onFetchAPoDBasedOnDateRangeCompleted(apods: List<APoD>) {
        view.hideNetworkRequestFailedState()
        view.hideProgressIndicator()
        view.onRefreshCompleted()
        view.bindApods(apods)
    }

    override fun onFetchAPoDBasedOnDateCompleted(apod: APoD) {
        screensNavigator.toDetailsScreen(apod)
    }

    override fun onFetchRandomAPoDCompleted(randomAPoD: List<APoD>) {
        screensNavigator.toDetailsScreen(randomAPoD.first())
    }

    override fun onFetchAPoDError(errorCode: String) {
        view.hideProgressIndicator()
        view.showNetworkRequestErrorState(errorCode)
    }

    override fun onEvent(event: Any) {
        when (event) {
            is ToolbarEvent -> onToolbarActionClickEvent(event.action)
            is DateSetEvent -> onDateSetEvent(event)
        }
    }

    private fun onToolbarActionClickEvent(action: ToolbarAction) {
        when (action) {
            ToolbarAction.PICK_APOD_DATE -> onToolbarActionPickApodByDateClicked()
            ToolbarAction.GET_RANDOM_APOD -> onToolbarActionGetRandomAPoDClicked()
            ToolbarAction.VISIT_APOD_WEBSITE -> onToolbarActionVisitApodWebsiteClicked()
        }
    }

    override fun onStart() {
        view.addListener(this)
        eventSubscriber.subscribe(this)
        fetchAPoDUseCase.addListener(this)
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventSubscriber.unsubscribe(this)
        fetchAPoDUseCase.removeListener(this)
        super.onStop()
    }

    override fun onDestroyView() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onDestroyView()
    }
}