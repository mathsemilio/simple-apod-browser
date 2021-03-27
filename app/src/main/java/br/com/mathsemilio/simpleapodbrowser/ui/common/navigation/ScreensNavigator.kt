package br.com.mathsemilio.simpleapodbrowser.ui.common.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.mathsemilio.simpleapodbrowser.common.INVALID_ROOT_FRAGMENT
import br.com.mathsemilio.simpleapodbrowser.common.OUT_STATE_TOP_DESTINATION
import br.com.mathsemilio.simpleapodbrowser.common.eventbus.EventPublisher
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.event.NavigationEvent
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.BottomNavItem
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.APoDDetailScreen
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodfavoriteslist.APoDFavoritesScreen
import br.com.mathsemilio.simpleapodbrowser.ui.screens.apodlist.APoDListScreen
import com.ncapdevi.fragnav.FragNavController

class ScreensNavigator(
    private val fragNavController: FragNavController,
    private val eventPublisher: EventPublisher
) {
    private lateinit var currentTopDestination: TopDestination

    private fun getRootFragmentListener(): FragNavController.RootFragmentListener {
        return object : FragNavController.RootFragmentListener {
            override val numberOfRootFragments get() = 2

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
        currentTopDestination = if (savedInstanceState != null) {
            savedInstanceState.getSerializable(OUT_STATE_TOP_DESTINATION) as TopDestination
        } else {
            TopDestination.LATEST_SCREEN
        }
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(OUT_STATE_TOP_DESTINATION, currentTopDestination)
        fragNavController.onSaveInstanceState(outState)
    }

    private fun publishEventToTopDestination(destination: TopDestination) {
        eventPublisher.publishEvent(NavigationEvent.ToTopDestination(destination))
    }

    private fun publishEventToSecondaryDestination(destination: SecondaryDestination) {
        eventPublisher.publishEvent(NavigationEvent.ToSecondaryDestination(destination))
    }

    fun switchTopDestinationsBasedOnBottomNavItem(item: BottomNavItem) {
        fragNavController.switchTab(
            when (item) {
                BottomNavItem.LATEST -> {
                    currentTopDestination = TopDestination.LATEST_SCREEN
                    publishEventToTopDestination(TopDestination.LATEST_SCREEN)
                    FragNavController.TAB1
                }
                BottomNavItem.FAVORITES -> {
                    currentTopDestination = TopDestination.FAVORITES_SCREEN
                    publishEventToTopDestination(TopDestination.FAVORITES_SCREEN)
                    FragNavController.TAB2
                }
            }
        )
    }

    fun toAPoDDetailsScreen(apod: APoD, isFavoriteAPoD: Boolean = false) {
        fragNavController.pushFragment(APoDDetailScreen.newInstance(apod))
        eventPublisher.publishEvent(
            when (isFavoriteAPoD) {
                true -> publishEventToSecondaryDestination(SecondaryDestination.FAVORITE_APOD_DETAILS)
                false -> publishEventToSecondaryDestination(SecondaryDestination.APOD_DETAILS)
            }
        )
    }

    fun navigateUp(): Boolean {
        return if (!fragNavController.isRootFragment) {
            fragNavController.popFragment()
            publishEventToTopDestination(currentTopDestination)
            true
        } else {
            false
        }
    }
}