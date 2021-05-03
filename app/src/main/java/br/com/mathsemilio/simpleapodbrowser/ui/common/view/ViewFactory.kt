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
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.APoDDetailViewImpl
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.view.listitem.APoDFavoritesListItemViewImpl
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