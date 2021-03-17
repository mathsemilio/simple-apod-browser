package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import androidx.fragment.app.FragmentManager
import br.com.mathsemilio.simpleapodbrowser.common.APOD_TYPE_IMAGE
import br.com.mathsemilio.simpleapodbrowser.common.APOD_TYPE_VIDEO
import br.com.mathsemilio.simpleapodbrowser.common.INVALID_APOD_TYPE
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.NavigationEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsimage.APoDDetailsImageScreen
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsvideo.APoDDetailsVideoScreen
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavorites.APoDFavoritesScreen
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.APoDListScreen
import java.io.Serializable

class ScreensNavigator(
    private val eventPoster: EventPoster,
    private val fragmentManager: FragmentManager,
    private val fragmentContainerHelper: FragmentContainerHelper
) {
    fun navigateToAPoDListScreen() {
        eventPoster.postEvent(NavigationEvent.OnNavigate(true, NavDestination.LATEST_SCREEN))
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            replace(fragmentContainerHelper.getFragmentContainer().id, APoDListScreen())
            commitNow()
        }
    }

    fun navigateToAPoDFavoritesScreen() {
        eventPoster.postEvent(NavigationEvent.OnNavigate(true, NavDestination.FAVORITES_SCREEN))
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            replace(fragmentContainerHelper.getFragmentContainer().id, APoDFavoritesScreen())
            commitNow()
        }
    }

    fun <T : Serializable> navigateToAPoDDetailsScreen(apod: T, apodType: String) {
        eventPoster.postEvent(NavigationEvent.OnNavigate(destination = NavDestination.APOD_DETAILS_SCREEN))
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(
                android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right
            )
            replace(
                fragmentContainerHelper.getFragmentContainer().id,
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
        }
    }
}