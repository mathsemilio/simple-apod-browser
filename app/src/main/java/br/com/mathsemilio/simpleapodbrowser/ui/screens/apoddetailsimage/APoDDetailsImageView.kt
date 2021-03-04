package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsimage

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseView

class APoDDetailsImageView(layoutInflater: LayoutInflater, container: ViewGroup?) : BaseView(),
    APoDDetailsImageContract.View {

    init {
        rootView = layoutInflater.inflate(R.layout.apod_detail_with_image, container, false)
    }

    override fun bindAPoDDetails(apod: APoD) {
        TODO("Not yet implemented")
    }
}