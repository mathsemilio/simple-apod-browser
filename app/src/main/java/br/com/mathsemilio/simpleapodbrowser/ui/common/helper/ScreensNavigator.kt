package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import androidx.fragment.app.FragmentManager
import br.com.mathsemilio.simpleapodbrowser.common.APOD_TYPE_IMAGE
import br.com.mathsemilio.simpleapodbrowser.common.APOD_TYPE_VIDEO
import br.com.mathsemilio.simpleapodbrowser.common.INVALID_APOD_TYPE
import br.com.mathsemilio.simpleapodbrowser.common.event.NavigationEvent
import br.com.mathsemilio.simpleapodbrowser.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsimage.APoDDetailsImageScreen
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsvideo.APoDDetailsVideoScreen
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites.APoDFavoritesScreen
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.APoDListScreen
import java.io.Serializable
import java.lang.RuntimeException

class ScreensNavigator(
    private val eventPoster: EventPoster,
    private val fragmentManager: FragmentManager,
    private val fragmentContainerHelper: FragmentContainerHelper
) {
    fun navigateToAPoDListScreen() {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            replace(fragmentContainerHelper.fragmentContainer.id, APoDListScreen())
            commitNow()
            eventPoster.postEvent(NavigationEvent(NavDestination.EXPLORE_SCREEN))
        }
    }

    fun navigateToAPoDFavoritesScreen() {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            replace(fragmentContainerHelper.fragmentContainer.id, APoDFavoritesScreen())
            commitNow()
            eventPoster.postEvent(NavigationEvent(NavDestination.FAVORITES_SCREEN))
        }
    }

    fun <T : Serializable> navigateToAPoDDetailsScreen(apod: T, apodType: String) {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(
                android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right
            )
            replace(
                fragmentContainerHelper.fragmentContainer.id,
                when (apodType) {
                    APOD_TYPE_IMAGE -> {
                        APoDDetailsImageScreen.newInstance(apod)
                    }
                    APOD_TYPE_VIDEO -> {
                        APoDDetailsVideoScreen.newInstance(apod)
                    }
                    else -> throw RuntimeException(INVALID_APOD_TYPE)
                }
            )
            addToBackStack(null)
            commit()
            eventPoster.postEvent(NavigationEvent(NavDestination.APOD_DETAILS_SCREEN))
        }
    }
}