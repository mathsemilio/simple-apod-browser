package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsvideo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster

class APoDDetailsVideoScreen : BaseFragment(),
    APoDDetailsVideoContract.View.Listener,
    APoDDetailsVideoContract.Screen,
    EventPoster.EventListener {

    companion object {
        fun newInstance(apod: APoD): APoDDetailsVideoScreen {
            val args = Bundle(1).apply { putSerializable(ARG_APOD, apod) }
            val fragment = APoDDetailsVideoScreen()
            fragment.arguments = args
            return fragment
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

    override fun onToolbarActionAddToFavoritesClicked() {
        TODO("Not yet implemented")
    }

    override fun onButtonPlayClicked() {
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

    override fun onDestroyView() {
        super.onDestroyView()
    }
}