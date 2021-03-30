package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

abstract class APoDListScreenView : BaseObservableView<APoDListScreenView.Listener>() {

    interface Listener {
        fun onApodClicked(apod: APoD)

        fun onScreenSwipedToRefresh()
    }

    abstract fun bindApods(apods: List<APoD>)

    abstract fun showProgressIndicator()

    abstract fun hideProgressIndicator()

    abstract fun onRefreshCompleted()

    abstract fun showNetworkRequestErrorState(errorMessage: String)

    abstract fun hideNetworkRequestFailedState()
}