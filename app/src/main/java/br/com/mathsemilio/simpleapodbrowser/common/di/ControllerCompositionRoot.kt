package br.com.mathsemilio.simpleapodbrowser.common.di

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    val coroutineScopeProvider get() = activityCompositionRoot.coroutineScopeProvider

    val eventPublisher get() = activityCompositionRoot.eventPublisher

    val eventSubscriber get() = activityCompositionRoot.eventSubscriber

    val dialogManager get() = activityCompositionRoot.dialogManager

    val messagesManager get() = activityCompositionRoot.messagesManager

    val screensNavigator get() = activityCompositionRoot.screensNavigator

    val statusBarManager get() = activityCompositionRoot.statusBarManager

    val rootLayoutHelper get() = activityCompositionRoot.hostLayoutHelper

    val viewFactory get() = activityCompositionRoot.viewFactory

    val fetchAPoDUseCase get() = activityCompositionRoot.fetchAPoDUseCase

    val fetchFavoriteAPoDUseCase get() = activityCompositionRoot.fetchFavoriteAPoDUseCase

    val deleteFavoriteAPoDUseCase get() = activityCompositionRoot.deleteFavoriteAPoDUseCase

    val addFavoriteAPodUseCase get() = activityCompositionRoot.addFavoriteAPodUseCase
}