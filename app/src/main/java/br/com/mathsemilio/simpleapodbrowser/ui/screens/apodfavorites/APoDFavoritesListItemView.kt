package br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseObservableView

class APoDFavoritesListItemView(layoutInflater: LayoutInflater, parent: ViewGroup?) :
    BaseObservableView<APoDFavoritesContract.ListItem.Listener>(), APoDFavoritesContract.ListItem {

    init {
        rootView = layoutInflater.inflate(R.layout.apod_list_item_favorite, parent, false)
    }

    override fun bindAPoDDetails(apod: APoD) {
        TODO("Not yet implemented")
    }
}