package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.mathsemilio.simpleapodbrowser.common.INVALID_ROOT_FRAGMENT
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.NavigationEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.poster.EventPoster
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.TopDestination
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.APoDDetailScreen
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.APoDListScreen
import com.ncapdevi.fragnav.FragNavController

class ScreensNavigator(
    private val fragNavController: FragNavController,
    private val eventPoster: EventPoster
) {
    private fun getRootFragmentListener(): FragNavController.RootFragmentListener {
        return object : FragNavController.RootFragmentListener {
            override val numberOfRootFragments get() = 1

            override fun getRootFragment(index: Int): Fragment {
                return when (index) {
                    FragNavController.TAB1 -> APoDListScreen()
                    else -> throw IllegalStateException(INVALID_ROOT_FRAGMENT + index)
                }
            }
        }
    }

    fun initialize(savedInstanceState: Bundle?) {
        fragNavController.rootFragmentListener = getRootFragmentListener()
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)
    }

    fun onSaveInstanceState(outState: Bundle) {
        fragNavController.onSaveInstanceState(outState)
    }

    fun navigateUp(): Boolean {
        return if (fragNavController.isRootFragment) {
            false
        } else {
            fragNavController.popFragment()
            eventPoster.postEvent(NavigationEvent.ToTopDestination(TopDestination.LATEST_SCREEN))
            true
        }
    }

    fun toDetailsScreen(apod: APoD) {
        fragNavController.pushFragment(APoDDetailScreen.newInstance(apod))
        eventPoster.postEvent(NavigationEvent.ToDestination(NavDestination.DETAILS_SCREEN))
    }
}