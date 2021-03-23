package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

abstract class APoDDetailView : BaseObservableView<APoDDetailView.Listener>() {

    interface Listener {
        fun onPlayIconClicked(videoUrl: String)
    }

    abstract fun bindAPoDDetails(apod: APoD)
}