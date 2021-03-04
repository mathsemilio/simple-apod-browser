package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsimage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD
import br.com.mathsemilio.simpleapodbrowser.common.ILLEGAL_TOOLBAR_ACTION
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.ToolbarActionClickEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.ToolbarAction
import java.io.Serializable

class APoDDetailsImageScreen : BaseFragment(),
    APoDDetailsImageContract.Screen,
    EventPoster.EventListener {

    companion object {
        fun <T: Serializable> newInstance(apod: T): APoDDetailsImageScreen {
            val args = Bundle(1).apply { putSerializable(ARG_APOD, apod) }
            val aPoDDetailsImageScreen = APoDDetailsImageScreen()
            aPoDDetailsImageScreen.arguments = args
            return aPoDDetailsImageScreen
        }
    }

    private lateinit var view: APoDDetailsImageView
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
        view = compositionRoot.viewFactory.getApodDetailsImageView(container)
        return view.rootView
    }

    override fun getAPoD(): APoD {
        return arguments?.getSerializable(ARG_APOD) as APoD
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
        eventPoster.addListener(this)
        super.onStart()
    }

    override fun onStop() {
        eventPoster.removeListener(this)
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}