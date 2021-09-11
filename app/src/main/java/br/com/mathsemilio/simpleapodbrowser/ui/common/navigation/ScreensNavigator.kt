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
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod

class ScreensNavigator(private val navController: NavController) {

    fun toApodDetailsScreen(apod: Apod) {
        val argApod = Bundle(1).apply { putSerializable(ARG_APOD, apod) }
        navController.navigate(R.id.action_apod_list_to_apod_detail_screen, argApod)
    }

    fun toFavoriteApodDetailsScreen(favoriteApod: Apod) {
        val argFavoriteApod = Bundle(1).apply { putSerializable(ARG_APOD, favoriteApod) }
        navController.navigate(R.id.action_apod_favorites_to_apod_detail_screen, argFavoriteApod)
    }

    fun toApodImageDetail(apodImage: ByteArray) {
        val argApodImage = Bundle(1).apply { putByteArray(ARG_APOD_IMAGE, apodImage) }
        navController.navigate(R.id.action_apod_detail_to_apod_image_detail_screen, argApodImage)
    }

    fun toSettingsScreen() {
        navController.navigate(R.id.action_global_settings)
    }

    fun navigateUp() {
        navController.navigateUp()
    }
}