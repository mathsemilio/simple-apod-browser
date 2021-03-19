package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsvideo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment

class APoDDetailsVideoScreen : BaseFragment(), APoDDetailsVideoContract.Screen {

    companion object {
        fun newInstance(apod: APoD): APoDDetailsVideoScreen {
            val args = Bundle().apply { putSerializable(ARG_APOD, apod) }
            val aPoDDetailsVideoScreen = APoDDetailsVideoScreen()
            aPoDDetailsVideoScreen.arguments = args
            return aPoDDetailsVideoScreen
        }
    }

    private lateinit var view: APoDDetailsVideoView

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

    override fun bindAPoD() {
        view.bindAPoDDetails(getAPoD())
    }

    override fun onStart() {
        bindAPoD()
        super.onStart()
    }
}