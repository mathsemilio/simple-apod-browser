package br.com.mathsemilio.simpleapodbrowser.ui.common.helper

import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.mathsemilio.simpleapodbrowser.common.INVALID_ROOT_FRAGMENT
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventPublisher
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.NavigationEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.BottomNavItem
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.TopDestination
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.APoDDetailScreen
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.APoDFavoritesScreen
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.APoDListScreen
import com.ncapdevi.fragnav.FragNavController

class ScreensNavigator(
    private val fragNavController: FragNavController,
    private val eventPublisher: EventPublisher
) {
    private fun getRootFragmentListener(): FragNavController.RootFragmentListener {
        return object : FragNavController.RootFragmentListener {
            override val numberOfRootFragments get() = 1

            override fun getRootFragment(index: Int): Fragment {
                return when (index) {
                    FragNavController.TAB1 -> APoDListScreen()
                    FragNavController.TAB2 -> APoDFavoritesScreen()
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
            toLatestScreen()
            true
        }
    }

    fun switchTopDestinations(item: BottomNavItem) {
        fragNavController.switchTab(
            when (item) {
                BottomNavItem.LATEST -> {
                    eventPublisher.publishEvent(
                        NavigationEvent.ToTopDestination(TopDestination.LATEST_SCREEN)
                    )
                    FragNavController.TAB1
                }
                BottomNavItem.FAVORITES -> {
                    eventPublisher.publishEvent(
                        NavigationEvent.ToTopDestination(TopDestination.FAVORITES_SCREEN)
                    )
                    FragNavController.TAB2
                }
            }
        )
    }

    private fun toLatestScreen() {
        fragNavController.clearStack()
        fragNavController.pushFragment(APoDListScreen())
        eventPublisher.publishEvent(NavigationEvent.ToTopDestination(TopDestination.LATEST_SCREEN))
    }

    fun toAPoDDetailsScreen(apod: APoD) {
        fragNavController.pushFragment(APoDDetailScreen.newInstance(apod))
        eventPublisher.publishEvent(NavigationEvent.ToDestination(NavDestination.DETAILS_SCREEN))
    }
}