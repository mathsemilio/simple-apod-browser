package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.ILLEGAL_TOOLBAR_ACTION
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarActionClickEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

class APoDListScreen : BaseFragment(),
    APoDListContract.View.Listener,
    APoDListContract.Screen,
    EventPoster.EventListener {

    private lateinit var view: APoDListScreenView
    private lateinit var eventPoster: EventPoster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventPoster = compositionRoot.eventPoster
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
        TODO("Not yet implemented")
    }

    override fun onScreenSwipedToRefresh() {
        TODO("Not yet implemented")
    }

    override fun fetchApods() {
        TODO("Not yet implemented")
    }

    override fun onFetchApodsStarted() {
        TODO("Not yet implemented")
    }

    override fun onFetchApodsCompleted(apods: List<APoD>) {
        TODO("Not yet implemented")
    }

    override fun onFetchApodsFailed(errorMessage: String) {
        TODO("Not yet implemented")
    }

    override fun onToolbarActionSearchClicked() {
        TODO("Not yet implemented")
    }

    override fun onToolbarActionPickApodByDateClicked() {
        TODO("Not yet implemented")
    }

    override fun onEvent(event: Any) {
        when (event) {
            is ToolbarActionClickEvent -> handleToolbarActionClickEvent(event.action)
        }
    }

    private fun handleToolbarActionClickEvent(action: ToolbarAction) {
        when (action) {
            ToolbarAction.SEARCH_EXPLORE -> onToolbarActionSearchClicked()
            ToolbarAction.PICK_APOD_BY_DATE -> onToolbarActionPickApodByDateClicked()
            else -> throw IllegalArgumentException(ILLEGAL_TOOLBAR_ACTION)
        }
    }

    override fun onStart() {
        view.addListener(this)
        eventPoster.addListener(this)
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        eventPoster.removeListener(this)
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}