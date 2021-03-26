package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

abstract class APoDFavoritesScreenView : BaseObservableView<APoDFavoritesScreenView.Listener>() {

    interface Listener {
        fun onFavoriteAPoDClicked(apod: APoD)

        fun onRemoveFromFavoritesIconClicked(apod: APoD)
    }

    abstract fun bindFavoriteApods(favoriteApods: List<APoD>)

    abstract fun showProgressIndicator()

    abstract fun hideProgressIndicator()
}