package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster

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
        TODO("Not yet implemented")
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