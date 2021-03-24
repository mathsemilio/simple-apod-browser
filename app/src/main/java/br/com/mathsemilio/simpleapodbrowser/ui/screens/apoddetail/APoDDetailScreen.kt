package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD_SAVED_INSTANCE
import br.com.mathsemilio.simpleapodbrowser.common.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment

class APoDDetailScreen : BaseFragment(), APoDDetailView.Listener {

    companion object {
        fun newInstance(apod: APoD): APoDDetailScreen {
            val args = Bundle(1).apply { putSerializable(ARG_APOD, apod) }
            val aPoDDetailsImageScreen = APoDDetailScreen()
            aPoDDetailsImageScreen.arguments = args
            return aPoDDetailsImageScreen
        }
    }

    private lateinit var view: APoDDetailViewImpl

    private lateinit var currentAPoD: APoD

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = compositionRoot.viewFactory.getApodDetailsImageView(container)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentAPoD = if (savedInstanceState != null)
            savedInstanceState.getSerializable(ARG_APOD_SAVED_INSTANCE) as APoD
        else
            getAPoD()
    }

    override fun onPlayIconClicked(videoUrl: String) {
        requireContext().launchWebPage(videoUrl)
    }

    private fun getAPoD(): APoD {
        return arguments?.getSerializable(ARG_APOD) as APoD
    }

    private fun bindAPoD() {
        view.bindAPoDDetails(currentAPoD)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(ARG_APOD_SAVED_INSTANCE, currentAPoD)
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