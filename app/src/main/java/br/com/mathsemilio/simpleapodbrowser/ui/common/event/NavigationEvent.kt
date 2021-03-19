package br.com.mathsemilio.simpleapodbrowser.ui.common.event

import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination

sealed class NavigationEvent(val destination: NavDestination) {
    class OnNavigate(destination: NavDestination) : NavigationEvent(destination)
}