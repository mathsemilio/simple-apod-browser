package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsvideo

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

class APoDDetailsVideoView(layoutInflater: LayoutInflater, container: ViewGroup?) :
    BaseObservableView<APoDDetailsVideoContract.View.Listener>(),
    APoDDetailsVideoContract.View {

    init {
        rootView = layoutInflater.inflate(R.layout.apod_detail_with_video, container, false)
    }

    override fun bindAPoDDetails(aPoD: APoD) {
        TODO("Not yet implemented")
    }
}