package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

class APoDListItemView(layoutInflater: LayoutInflater, parent: ViewGroup?) :
    BaseObservableView<APoDListContract.ListItem.Listener>(), APoDListContract.ListItem {

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_item, parent, false)
    }

    override fun bindAPoDDetails(apod: APoD) {
        TODO("Not yet implemented")
    }
}