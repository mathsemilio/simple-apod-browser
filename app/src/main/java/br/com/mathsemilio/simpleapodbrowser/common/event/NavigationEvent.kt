package br.com.mathsemilio.simpleapodbrowser.common.event

import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination

class NavigationEvent(
    private val _navEvent: Event? = null,
    private val _navDestination: NavDestination
) {
    enum class Event { UPDATE_TOP_DESTINATION }

    val navEvent get() = _navEvent
    val navDestination get() = _navDestination
}