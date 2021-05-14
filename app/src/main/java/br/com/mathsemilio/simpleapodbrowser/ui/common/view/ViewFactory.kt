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
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.ApodDetailViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.listitem.ApodFavoritesListItemViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.ApodFavoritesScreenViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodimagedetailscreen.ApodImageDetailViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.listitem.ApodListItemViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.view.ApodListScreenViewImpl

class ViewFactory(private val layoutInflater: LayoutInflater) {

    fun getMainActivityView(parent: ViewGroup?) =
        MainActivityViewImpl(layoutInflater, parent)

    fun getApodListScreenView(container: ViewGroup?) =
        ApodListScreenViewImpl(layoutInflater, container, this)

    fun getApodListItemView(parent: ViewGroup?) =
        ApodListItemViewImpl(layoutInflater, parent)

    fun getApodDetailsView(container: ViewGroup?) =
        ApodDetailViewImpl(layoutInflater, container)

    fun getApodFavoritesScreenView(container: ViewGroup?) =
        ApodFavoritesScreenViewImpl(layoutInflater, container, this)

    fun getApodFavoritesListItemView(parent: ViewGroup?) =
        ApodFavoritesListItemViewImpl(layoutInflater, parent)

    fun getApodImageDetailView(container: ViewGroup?) =
        ApodImageDetailViewImpl(layoutInflater, container)
}