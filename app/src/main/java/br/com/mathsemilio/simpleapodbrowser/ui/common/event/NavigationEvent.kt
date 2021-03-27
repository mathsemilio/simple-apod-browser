package br.com.mathsemilio.simpleapodbrowser.ui.common.event

import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.SecondaryDestination
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.TopDestination

sealed class NavigationEvent(
    val topDestination: TopDestination? = null,
    val secondaryDestination: SecondaryDestination? = null
) {
    class ToTopDestination(destination: TopDestination) :
        NavigationEvent(topDestination = destination)

    class ToSecondaryDestination(destination: SecondaryDestination) :
        NavigationEvent(secondaryDestination = destination)
}