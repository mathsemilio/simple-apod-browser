package br.com.mathsemilio.simpleapodbrowser.common.di

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    val coroutineScopeProvider get() = activityCompositionRoot.coroutineScopeProvider

    val eventPoster get() = activityCompositionRoot.eventPoster

    val messagesManager get() = activityCompositionRoot.messagesManager

    val screensNavigator get() = activityCompositionRoot.screensNavigator

    val viewFactory get() = activityCompositionRoot.viewFactory

    val fetchAPoDUseCase get() = activityCompositionRoot.fetchAPoDUseCase
}