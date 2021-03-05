package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsvideo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD
import br.com.mathsemilio.simpleapodbrowser.common.ILLEGAL_TOOLBAR_ACTION
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.common.event.ToolbarActionClickEvent
import br.com.mathsemilio.simpleapodbrowser.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import java.io.Serializable

class APoDDetailsVideoScreen : BaseFragment(),
    APoDDetailsVideoContract.View.Listener,
    APoDDetailsVideoContract.Screen,
    EventPoster.EventListener {

    companion object {
        fun <T: Serializable> newInstance(apod: T): APoDDetailsVideoScreen {
            val args = Bundle(1).apply { putSerializable(ARG_APOD, apod) }
            val aPoDDetailsVideoScreen = APoDDetailsVideoScreen()
            aPoDDetailsVideoScreen.arguments = args
            return aPoDDetailsVideoScreen
        }
    }

    private lateinit var view: APoDDetailsVideoView
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
        view = compositionRoot.viewFactory.getApodDetailsVideoView(container)
        return view.rootView
    }

    override fun getAPoD(): APoD {
        return arguments?.getSerializable(ARG_APOD) as APoD
    }

    override fun onButtonPlayClicked() {
        TODO("Not yet implemented")
    }

    override fun onToolbarActionAddToFavoritesClicked() {
        TODO("Not yet implemented")
    }

    override fun handleToolbarActionClickEvent(action: ToolbarAction) {
        when (action) {
            ToolbarAction.ADD_TO_FAVORITES -> onToolbarActionAddToFavoritesClicked()
            else -> throw IllegalArgumentException(ILLEGAL_TOOLBAR_ACTION)
        }
    }

    override fun onEvent(event: Any) {
        when (event) {
           is ToolbarActionClickEvent -> handleToolbarActionClickEvent(event.action)
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

    override fun onDestroyView() {
        super.onDestroyView()
    }
}