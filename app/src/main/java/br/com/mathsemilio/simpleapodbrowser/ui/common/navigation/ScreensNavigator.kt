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
import android.view.View
import androidx.navigation.fragment.*
import androidx.navigation.NavController
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.*
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod

class ScreensNavigator(private val navController: NavController) {

    fun toApodDetailsScreen(apod: Apod) {
        val argumentBundle = Bundle(2)
        argumentBundle.putSerializable(ARG_APOD, apod)
        argumentBundle.putBoolean(ARG_IS_RANDOM_APOD, false)

        navController.navigate(R.id.action_apod_list_to_apod_detail_screen, argumentBundle)
    }

    fun toApodDetailsScreenWith(randomApod: Apod) {
        val argumentBundle = Bundle(2)
        argumentBundle.putSerializable(ARG_APOD, randomApod)
        argumentBundle.putBoolean(ARG_IS_RANDOM_APOD, true)

        navController.navigate(R.id.action_apod_list_to_apod_detail_screen, argumentBundle)
    }

    fun toFavoriteApodDetailsScreen(favoriteApod: Apod) {
        val argumentBundle = Bundle(1)
        argumentBundle.putSerializable(ARG_APOD, favoriteApod)

        navController.navigate(R.id.action_apod_favorites_to_apod_detail_screen, argumentBundle)
    }

    fun toApodImageDetail(apodImage: ByteArray, transitionView: View) {
        val argumentBundle = Bundle(1)
        argumentBundle.putByteArray(ARG_APOD_IMAGE, apodImage)

        val navigatorExtras = FragmentNavigatorExtras(
            transitionView to TRANSITION_APOD_DETAIL_TO_IMAGE_DETAIL
        )

        navController.navigate(
            R.id.action_apod_detail_to_apod_image_detail_screen,
            argumentBundle,
            null,
            navigatorExtras
        )
    }

    fun toSettingsScreen() = navController.navigate(R.id.action_global_settings)

    fun navigateUp() = navController.navigateUp()
}
