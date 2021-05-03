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
package br.com.mathsemilio.simpleapodbrowser.ui.common.navigation

import android.os.Bundle
import androidx.navigation.NavController
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD
import br.com.mathsemilio.simpleapodbrowser.common.ARG_APOD_IMAGE
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD

class ScreensNavigator(private val navController: NavController) {

    fun toAPoDDetailsScreen(apod: APoD) {
        val argAPoD = Bundle(1).apply { putSerializable(ARG_APOD, apod) }
        navController.navigate(R.id.action_apod_list_to_apod_detail_screen, argAPoD)
    }

    fun toFavoriteAPoDDetailsScreen(favoriteAPoD: APoD) {
        val argFavoriteAPoD = Bundle(1).apply { putSerializable(ARG_APOD, favoriteAPoD) }
        navController.navigate(R.id.action_apod_favorites_to_apod_detail_screen, argFavoriteAPoD)
    }

    fun toAPoDImageDetail(apodImage: ByteArray) {
        val argAPoDImage = Bundle(1).apply { putByteArray(ARG_APOD_IMAGE, apodImage) }
        navController.navigate(R.id.action_apod_detail_to_apod_image_detail_screen, argAPoDImage)
    }

    fun navigateUp() {
        navController.navigateUp()
    }
}