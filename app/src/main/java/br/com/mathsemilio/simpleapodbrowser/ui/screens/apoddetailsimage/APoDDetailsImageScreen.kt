package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsimage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseFragment

class APoDDetailsImageScreen : BaseFragment(), APoDDetailsImageContract.Screen {

    companion object {
        fun newInstance(apod: APoD): APoDDetailsImageScreen {
            val args = Bundle(1).apply { putSerializable(ARG_APOD, apod) }
            val aPoDDetailsImageScreen = APoDDetailsImageScreen()
            aPoDDetailsImageScreen.arguments = args
            return aPoDDetailsImageScreen
        }
    }

    private lateinit var view: APoDDetailsImageView

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

    override fun onStart() {
        bindAPoD()
        super.onStart()
    }
}