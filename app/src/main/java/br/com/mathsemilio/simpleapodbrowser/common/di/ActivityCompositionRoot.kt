package br.com.mathsemilio.simpleapodbrowser.common.di

import androidx.appcompat.app.AppCompatActivity
import br.com.mathsemilio.simpleapodbrowser.common.provider.CoroutineScopeProvider
import br.com.mathsemilio.simpleapodbrowser.common.provider.DispatcherProvider
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.AddFavoriteAPodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.DeleteFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.APoDEndpoint
import br.com.mathsemilio.simpleapodbrowser.storage.database.AppDatabase
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteAPoDEndpoint
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.DialogManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.FragmentContainerHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.ViewFactory
import com.ncapdevi.fragnav.FragNavController

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val compositionRoot: CompositionRoot
) {
    private val aPoDApi get() = compositionRoot.retrofitBuilder.apodApi

    private val appDatabase get() = AppDatabase.getDatabase(activity)

    private val favoriteAPoDDAO get() = appDatabase.favoriteAPoDDAO

    private val aPoDCacheDAO get() = appDatabase.apodCacheDAO

    private val aPoDEndpoint by lazy {
        APoDEndpoint(aPoDApi, apiKeyProvider, dispatcherProvider)
    }

    private val favoriteAPoDEndpoint by lazy {
        FavoriteAPoDEndpoint(favoriteAPoDDAO, dispatcherProvider)
    }

    private val dispatcherProvider get() = DispatcherProvider

    private val _dialogManager by lazy {
        DialogManager(activity.supportFragmentManager)
    }

    private val _fetchAPoDUseCase by lazy {
        FetchAPoDUseCase(aPoDEndpoint)
    }

    private val _fetchFavoriteAPoDUseCase by lazy {
        FetchFavoriteAPoDUseCase(favoriteAPoDEndpoint)
    }

    private val _addFavoriteAPodUseCase by lazy {
        AddFavoriteAPodUseCase(favoriteAPoDEndpoint)
    }

    private val _deleteFavoriteAPoDUseCase by lazy {
        DeleteFavoriteAPoDUseCase(favoriteAPoDEndpoint)
    }

    private val fragmentContainerHelper get() = activity as FragmentContainerHelper

    private val _messagesManager by lazy {
        MessagesManager(activity)
    }

    private val _screensNavigator by lazy {
        ScreensNavigator(
            FragNavController(
                activity.supportFragmentManager,
                fragmentContainerHelper.getFragmentContainer().id
            ),
            eventPublisher
        )
    }

    private val _viewFactory by lazy {
        ViewFactory(activity.layoutInflater)
    }

    private val apiKeyProvider get() = compositionRoot.apiKeyProvider

    val coroutineScopeProvider get() = CoroutineScopeProvider

    val dialogManager get() = _dialogManager

    val eventPublisher get() = compositionRoot.eventPublisher

    val eventSubscriber get() = compositionRoot.eventSubscriber

    val messagesManager get() = _messagesManager

    val screensNavigator get() = _screensNavigator

    val viewFactory get() = _viewFactory

    val fetchAPoDUseCase get() = _fetchAPoDUseCase

    val fetchFavoriteAPoDUseCase get() = _fetchFavoriteAPoDUseCase

    val addFavoriteAPodUseCase get() = _addFavoriteAPodUseCase

    val deleteFavoriteAPoDUseCase get() = _deleteFavoriteAPoDUseCase
}