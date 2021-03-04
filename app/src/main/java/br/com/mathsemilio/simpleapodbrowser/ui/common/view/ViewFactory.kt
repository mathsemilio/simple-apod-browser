package br.com.mathsemilio.simpleapodbrowser.ui.common.view

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsimage.APoDDetailsImageView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsvideo.APoDDetailsVideoView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites.APoDFavoritesView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.APoDListScreenView

class ViewFactory(private val layoutInflater: LayoutInflater) {

    fun getApodListScreenView(container: ViewGroup?) =
        APoDListScreenView(layoutInflater, container)

    fun getApodFavoritesView(container: ViewGroup?) =
        APoDFavoritesView(layoutInflater, container)

    fun getApodDetailsVideoView(container: ViewGroup?) =
        APoDDetailsVideoView(layoutInflater, container)

    fun getApodDetailsImageView(container: ViewGroup?) =
        APoDDetailsImageView(layoutInflater, container)
}