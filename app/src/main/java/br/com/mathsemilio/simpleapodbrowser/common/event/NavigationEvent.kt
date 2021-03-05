package br.com.mathsemilio.simpleapodbrowser.common.event

import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination

class NavigationEvent(private val _navDestination: NavDestination) {
    val navDestination get() = _navDestination
}