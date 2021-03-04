package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

class APoDListScreenView(layoutInflater: LayoutInflater, container: ViewGroup?) :
    BaseObservableView<APoDListContract.View.Listener>(), APoDListContract.View {

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_screen, container, false)
    }

    override fun bindApods(apods: List<APoD>) {
        TODO("Not yet implemented")
    }

    override fun showProgressIndicator() {
        TODO("Not yet implemented")
    }

    override fun hideProgressIndicator() {
        TODO("Not yet implemented")
    }

    override fun onRefreshCompleted() {
        TODO("Not yet implemented")
    }
}