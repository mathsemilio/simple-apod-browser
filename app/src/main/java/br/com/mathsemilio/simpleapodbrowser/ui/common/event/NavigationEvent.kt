package br.com.mathsemilio.simpleapodbrowser.ui.common.event

import br.com.mathsemilio.simpleapodbrowser.ui.common.others.NavDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.others.TopDestination

sealed class NavigationEvent(
    val destination: NavDestination? = null,
    val topDestination: TopDestination? = null
) {
    class ToTopDestination(topDestination: TopDestination) :
        NavigationEvent(topDestination = topDestination)

    class ToDestination(destination: NavDestination) :
        NavigationEvent(destination = destination)
}