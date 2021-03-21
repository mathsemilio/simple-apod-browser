package br.com.mathsemilio.simpleapodbrowser.ui.common.view

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.common.provider.GlideProvider
import br.com.mathsemilio.simpleapodbrowser.ui.MainActivityView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.APoDDetailView
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.APoDListScreenView

class ViewFactory(
    private val layoutInflater: LayoutInflater,
    private val glideProvider: GlideProvider
) {
    fun getMainActivityView(parent: ViewGroup?) =
        MainActivityView(layoutInflater, parent)

    fun getApodListScreenView(container: ViewGroup?) =
        APoDListScreenView(glideProvider, layoutInflater, container)

    fun getApodDetailsImageView(container: ViewGroup?) =
        APoDDetailView(glideProvider, layoutInflater, container)
}