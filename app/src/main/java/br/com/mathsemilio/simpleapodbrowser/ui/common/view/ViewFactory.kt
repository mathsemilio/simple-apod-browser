package br.com.mathsemilio.simpleapodbrowser.ui.common.view

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.ui.MainActivityViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.APoDDetailViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.APoDListScreenViewImpl

class ViewFactory(private val layoutInflater: LayoutInflater) {

    fun getMainActivityView(parent: ViewGroup?) =
        MainActivityViewImpl(layoutInflater, parent)

    fun getApodListScreenView(container: ViewGroup?) =
        APoDListScreenViewImpl(layoutInflater, container)

    fun getApodDetailsImageView(container: ViewGroup?) =
        APoDDetailViewImpl(layoutInflater, container)
}