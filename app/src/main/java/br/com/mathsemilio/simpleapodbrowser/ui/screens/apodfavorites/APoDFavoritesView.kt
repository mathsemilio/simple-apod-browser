package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

class APoDFavoritesView(layoutInflater: LayoutInflater, container: ViewGroup?) :
    BaseObservableView<APoDFavoritesContract.View.Listener>(), APoDFavoritesContract.View {

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_favorites_screen, container, false)
    }

    override fun bindFavoriteApods(apods: List<APoD>) {
        TODO("Not yet implemented")
    }

    override fun showProgressIndicator() {
        TODO("Not yet implemented")
    }

    override fun hideProgressIndicator() {
        TODO("Not yet implemented")
    }
}