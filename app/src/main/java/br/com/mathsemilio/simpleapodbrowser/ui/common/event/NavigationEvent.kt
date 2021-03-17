package br.com.mathsemilio.simpleapodbrowser.ui.common.event

import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination

sealed class NavigationEvent(
    val updateTopLevelDestination: Boolean,
    val destination: NavDestination
) {
    class OnNavigate(
        updateTopLevelDestination: Boolean = false,
        destination: NavDestination
    ) : NavigationEvent(updateTopLevelDestination, destination)
}