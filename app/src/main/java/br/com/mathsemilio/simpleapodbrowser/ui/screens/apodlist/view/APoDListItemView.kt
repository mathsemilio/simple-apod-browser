package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

abstract class APoDListItemView : BaseObservableView<APoDListItemView.Listener>() {

    interface Listener {
        fun onAPoDClicked(apod: APoD)
    }

    abstract fun bindAPoDDetails(apod: APoD)
}