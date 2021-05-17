/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package br.com.mathsemilio.simpleapodbrowser.ui.common.view

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mathsemilio.simpleapodbrowser.ui.MainActivityViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.dialog.promptdialog.PromptDialogViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.ApodDetailViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.ApodFavoritesScreenViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.listitem.ApodFavoritesListItemViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodimagedetailscreen.ApodImageDetailViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.ApodListScreenViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.listitem.ApodListItemViewImpl

class ViewFactory(private val inflater: LayoutInflater) {

    val promptDialogView get() = PromptDialogViewImpl(inflater)

    fun getMainActivityView(parent: ViewGroup?) =
        MainActivityViewImpl(inflater, parent)

    fun getApodListScreenView(container: ViewGroup?) =
        ApodListScreenViewImpl(inflater, container, this)

    fun getApodListItemView(parent: ViewGroup?) =
        ApodListItemViewImpl(inflater, parent)

    fun getApodDetailsView(container: ViewGroup?) =
        ApodDetailViewImpl(inflater, container)

    fun getApodFavoritesScreenView(container: ViewGroup?) =
        ApodFavoritesScreenViewImpl(inflater, container, this)

    fun getApodFavoritesListItemView(parent: ViewGroup?) =
        ApodFavoritesListItemViewImpl(inflater, parent)

    fun getApodImageDetailView(container: ViewGroup?) =
        ApodImageDetailViewImpl(inflater, container)
}