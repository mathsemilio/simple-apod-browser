package br.com.mathsemilio.simpleapodbrowser.ui.common.event

import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination

sealed class NavigationEvent(
    val updateTopDestination: Boolean,
    val destination: NavDestination
) {
    class Navigate(
        updateTopDestination: Boolean = false,
        destination: NavDestination
    ) : NavigationEvent(updateTopDestination, destination)
}