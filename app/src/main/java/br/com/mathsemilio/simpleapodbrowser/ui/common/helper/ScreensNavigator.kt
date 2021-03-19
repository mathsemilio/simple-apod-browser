package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import androidx.fragment.app.FragmentManager
import br.com.mathsemilio.simpleapodbrowser.common.APOD_TYPE_IMAGE
import br.com.mathsemilio.simpleapodbrowser.common.APOD_TYPE_VIDEO
import br.com.mathsemilio.simpleapodbrowser.common.INVALID_APOD_TYPE
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.NavigationEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsimage.APoDDetailsImageScreen
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsvideo.APoDDetailsVideoScreen
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.APoDListScreen

class ScreensNavigator(
    private val fragmentManager: FragmentManager,
    private val containerManager: FragmentContainerManager,
    private val eventPoster: EventPoster
) {
    fun navigateToLatestScreen() {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            replace(containerManager.getFragmentContainer().id, APoDListScreen())
            commitNow()
        }
        eventPoster.postEvent(NavigationEvent.OnNavigate(NavDestination.LATEST_SCREEN))
    }

    fun navigateToDetailsScreen(apod: APoD, apodType: String) {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(
                android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right
            )
            replace(
                containerManager.getFragmentContainer().id,
                when (apodType) {
                    APOD_TYPE_IMAGE -> APoDDetailsImageScreen.newInstance(apod)
                    APOD_TYPE_VIDEO -> APoDDetailsVideoScreen.newInstance(apod)
                    else -> throw RuntimeException(INVALID_APOD_TYPE)
                }
            )
            addToBackStack(null)
            commit()
        }
        eventPoster.postEvent(NavigationEvent.OnNavigate(NavDestination.DETAILS_SCREEN))
    }
}