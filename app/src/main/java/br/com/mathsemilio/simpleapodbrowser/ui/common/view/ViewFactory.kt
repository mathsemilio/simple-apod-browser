package br.com.mathsemilio.simpleapodbrowser.ui.common.view

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.ui.MainActivityView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.APoDDetailView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.APoDListScreenView

class ViewFactory(private val layoutInflater: LayoutInflater) {

    fun getMainActivityView(parent: ViewGroup?) =
        MainActivityView(layoutInflater, parent)

    fun getApodListScreenView(container: ViewGroup?) =
        APoDListScreenView(layoutInflater, container)

    fun getApodDetailsImageView(container: ViewGroup?) =
        APoDDetailView(layoutInflater, container)
}