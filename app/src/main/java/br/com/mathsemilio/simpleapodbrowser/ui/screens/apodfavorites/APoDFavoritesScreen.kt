package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.ILLEGAL_TOOLBAR_ACTION
import br.com.mathsemilio.simpleapodbrowser.common.event.ToolbarActionClickEvent
import br.com.mathsemilio.simpleapodbrowser.common.event.ToolbarSearchViewEvent
import br.com.mathsemilio.simpleapodbrowser.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction

class APoDFavoritesScreen : BaseFragment(),
    APoDFavoritesContract.View.Listener,
    APoDFavoritesContract.Screen,
    EventPoster.EventListener {

    private lateinit var view: APoDFavoritesView
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
        view = compositionRoot.viewFactory.getApodFavoritesView(container)
        return view.rootView
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

    override fun onFavoriteAPoDClicked(apod: APoD) {
        TODO("Not yet implemented")
    }

    override fun handleToolbarSearchViewEvent(
        event: ToolbarSearchViewEvent.Event,
        textEnteredByUser: String
    ) {
        when (event) {
            ToolbarSearchViewEvent.Event.TEXT_ENTERED_BY_USER -> TODO()
        }
    }

    override fun handleToolbarActionClickEvent(action: ToolbarAction) {
        when (action) {
            ToolbarAction.VISIT_APOD_WEBSITE -> TODO()
            else -> throw IllegalArgumentException(ILLEGAL_TOOLBAR_ACTION)
        }
    }

    override fun onEvent(event: Any) {
        when (event) {
            is ToolbarSearchViewEvent ->
                handleToolbarSearchViewEvent(event.searchViewEvent, event.textEnteredByUser)
            is ToolbarActionClickEvent ->
                handleToolbarActionClickEvent(event.action)
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