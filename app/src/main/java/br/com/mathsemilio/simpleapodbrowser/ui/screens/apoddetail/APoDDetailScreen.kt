package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD
import br.com.mathsemilio.simpleapodbrowser.common.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment

class APoDDetailScreen : BaseFragment(),
    APoDDetailContract.Screen,
    APoDDetailContract.View.Listener {

    companion object {
        fun newInstance(apod: APoD): APoDDetailScreen {
            val args = Bundle(1).apply { putSerializable(ARG_APOD, apod) }
            val aPoDDetailsImageScreen = APoDDetailScreen()
            aPoDDetailsImageScreen.arguments = args
            return aPoDDetailsImageScreen
        }
    }

    private lateinit var view: APoDDetailView

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

    override fun bindAPoD() {
        view.bindAPoDDetails(getAPoD())
    }

    override fun onPlayIconClicked(videoUrl: String) {
        requireContext().launchWebPage(videoUrl)
    }

    override fun onStart() {
        view.addListener(this)
        bindAPoD()
        super.onStart()
    }

    override fun onStop() {
        view.removeListener(this)
        super.onStop()
    }
}