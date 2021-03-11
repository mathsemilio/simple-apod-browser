package br.com.mathsemilio.simpleapodbrowser.common.di

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    val coroutineScopeProvider get() = activityCompositionRoot.coroutineScopeProvider

    val eventPoster get() = activityCompositionRoot.eventPoster

    val dialogManager get() = activityCompositionRoot.dialogManager
    
    val messagesManager get() = activityCompositionRoot.messagesManager

    val screensNavigator get() = activityCompositionRoot.screensNavigator

    val viewFactory get() = activityCompositionRoot.viewFactory

    val fetchAPoDUseCase get() = activityCompositionRoot.fetchAPoDUseCase

    val addFavoriteAPoDUseCase get() = activityCompositionRoot.addFavoriteAPoDUseCase

    val fetchFavoriteAPoDUseCase get() = activityCompositionRoot.fetchFavoriteAPoDUseCase

    val deleteFavoriteAPoDUseCase get() = activityCompositionRoot.deleteFavoriteAPoDUseCase
}