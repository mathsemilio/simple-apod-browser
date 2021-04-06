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