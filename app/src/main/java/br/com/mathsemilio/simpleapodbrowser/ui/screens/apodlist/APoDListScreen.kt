package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.ILLEGAL_TOOLBAR_ACTION
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventListener
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventSubscriber
import br.com.mathsemilio.simpleapodbrowser.common.getLastSevenDays
import br.com.mathsemilio.simpleapodbrowser.common.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.DateSetEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.DialogManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.APoDListScreenView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.APoDListScreenViewImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class APoDListScreen : BaseFragment(),
    APoDListScreenView.Listener,
    FetchAPoDUseCase.Listener,
    EventListener {

    private lateinit var view: APoDListScreenViewImpl

    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var messagesManager: MessagesManager
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var dialogManager: DialogManager

    private lateinit var eventSubscriber: EventSubscriber

    private lateinit var fetchAPoDUseCase: FetchAPoDUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screensNavigator = compositionRoot.screensNavigator
        messagesManager = compositionRoot.messagesManager
        coroutineScope = compositionRoot.coroutineScopeProvider.UIBoundScope
        dialogManager = compositionRoot.dialogManager
        eventSubscriber = compositionRoot.eventSubscriber
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

    private fun fetchApods() {
        coroutineScope.launch {
            view.showProgressIndicator()
            fetchAPoDUseCase.fetchAPoDBasedOnDateRange(getLastSevenDays())
        }
    }

    private fun onToolbarActionClickEvent(action: ToolbarAction) {
        when (action) {
            ToolbarAction.PICK_APOD_DATE ->
                dialogManager.showDatePickerDialog()
            ToolbarAction.GET_RANDOM_APOD -> coroutineScope.launch {
                fetchAPoDUseCase.fetchRandomAPoD()
            }
            ToolbarAction.VISIT_APOD_WEBSITE ->
                requireContext().launchWebPage(getString(R.string.apod_website_url))
            else -> throw IllegalArgumentException(ILLEGAL_TOOLBAR_ACTION)
        }
    }

    private fun onDateSetEvent(event: DateSetEvent) {
        when (event) {
            is DateSetEvent.DateSet -> coroutineScope.launch {
                fetchAPoDUseCase.fetchAPoDBasedOnDate(event.dateInMillis)
            }
            DateSetEvent.InvalidDateSet ->
                messagesManager.showInvalidAPoDDateErrorMessage()
        }
    }

    override fun onApodClicked(apod: APoD) {
        screensNavigator.toAPoDDetailsScreen(apod)
    }

    override fun onScreenSwipedToRefresh() {
        fetchApods()
    }

    override fun onFetchAPoDBasedOnDateRangeCompleted(apods: List<APoD>) {
        view.hideNetworkRequestFailedState()
        view.hideProgressIndicator()
        view.onRefreshCompleted()
        view.bindApods(apods)
    }

    override fun onFetchAPoDBasedOnDateCompleted(apod: APoD) {
        screensNavigator.toAPoDDetailsScreen(apod)
    }

    override fun onFetchRandomAPoDCompleted(randomAPoD: APoD) {
        screensNavigator.toAPoDDetailsScreen(randomAPoD)
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

    override fun onStart() {
        view.addListener(this)
        eventSubscriber.subscribe(this)
        fetchAPoDUseCase.addListener(this)
        fetchApods()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventSubscriber.unsubscribe(this)
        fetchAPoDUseCase.removeListener(this)
        coroutineScope.coroutineContext.cancelChildren()
        super.onStop()
    }
}