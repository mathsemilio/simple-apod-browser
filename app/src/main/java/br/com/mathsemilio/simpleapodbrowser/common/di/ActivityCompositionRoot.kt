package br.com.mathsemilio.simpleapodbrowser.common.di

import androidx.appcompat.app.AppCompatActivity
import br.com.mathsemilio.simpleapodbrowser.common.provider.CoroutineScopeProvider
import br.com.mathsemilio.simpleapodbrowser.common.provider.DispatcherProvider
import br.com.mathsemilio.simpleapodbrowser.common.provider.GlideProvider
import br.com.mathsemilio.simpleapodbrowser.data.repository.APoDRepository
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.AddFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.DeleteFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.FetchAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.FetchFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.storage.database.FavoriteAPoDDatabase
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.DialogManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.FragmentContainerHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.ViewFactory

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val compositionRoot: CompositionRoot
) {
    private val aPoDApi get() = compositionRoot.retrofitBuilder.apodApi

    private val favoriteAPoDDatabase get() = FavoriteAPoDDatabase.getDatabase(activity)

    private val favoriteAPoDDAO get() = favoriteAPoDDatabase.favoriteApodDAO

    private val apodRepository get() = APoDRepository(aPoDApi, favoriteAPoDDAO)

    private val dispatcherProvider get() = DispatcherProvider

    val coroutineScopeProvider get() = CoroutineScopeProvider

    val eventPoster get() = compositionRoot.eventPoster

    private val _dialogManager by lazy {
        DialogManager(activity.supportFragmentManager)
    }
    val dialogManager get() = _dialogManager

    private val glideProvider by lazy { GlideProvider(activity) }

    private val _messagesManager by lazy {
        MessagesManager(activity, eventPoster)
    }
    val messagesManager get() = _messagesManager

    private val _screensNavigator by lazy {
        ScreensNavigator(
            eventPoster,
            activity.supportFragmentManager,
            activity as FragmentContainerHelper
        )
    }
    val screensNavigator get() = _screensNavigator

    private val _viewFactory by lazy {
        ViewFactory(activity.layoutInflater, glideProvider)
    }
    val viewFactory get() = _viewFactory

    private val _addFavoriteAPoDUseCase by lazy {
        AddFavoriteAPoDUseCase(apodRepository, dispatcherProvider)
    }
    val addFavoriteAPoDUseCase get() = _addFavoriteAPoDUseCase

    private val _fetchAPoDUseCase by lazy {
        FetchAPoDUseCase(apodRepository, dispatcherProvider)
    }
    val fetchAPoDUseCase get() = _fetchAPoDUseCase

    private val _fetchFavoriteAPoDUseCase by lazy {
        FetchFavoriteAPoDUseCase(apodRepository, dispatcherProvider)
    }
    val fetchFavoriteAPoDUseCase get() = _fetchFavoriteAPoDUseCase

    private val _deleteFavoriteAPoDUseCase by lazy {
        DeleteFavoriteAPoDUseCase(apodRepository, dispatcherProvider)
    }
    val deleteFavoriteAPoDUseCase get() = _deleteFavoriteAPoDUseCase
}