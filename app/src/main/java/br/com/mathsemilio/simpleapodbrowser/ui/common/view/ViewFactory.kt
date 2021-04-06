package br.com.mathsemilio.simpleapodbrowser.ui.common.view

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.ui.MainActivityViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.APoDDetailViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.APoDFavoritesListItemViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.APoDFavoritesScreenViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodimagedetailscreen.APoDImageDetailViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.APoDListItemViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.APoDListScreenViewImpl

class ViewFactory(private val layoutInflater: LayoutInflater) {

    fun getMainActivityView(parent: ViewGroup?) =
        MainActivityViewImpl(layoutInflater, parent)

    fun getAPoDListScreenView(container: ViewGroup?) =
        APoDListScreenViewImpl(layoutInflater, container, this)

    fun getAPoDListItemView(parent: ViewGroup?) =
        APoDListItemViewImpl(layoutInflater, parent)

    fun getAPoDDetailsView(container: ViewGroup?) =
        APoDDetailViewImpl(layoutInflater, container)

    fun getAPoDFavoritesScreenView(container: ViewGroup?) =
        APoDFavoritesScreenViewImpl(layoutInflater, container, this)

    fun getAPoDFavoritesListItemView(parent: ViewGroup?) =
        APoDFavoritesListItemViewImpl(layoutInflater, parent)

    fun getAPoDImageDetailView(container: ViewGroup?) =
        APoDImageDetailViewImpl(layoutInflater, container)
}