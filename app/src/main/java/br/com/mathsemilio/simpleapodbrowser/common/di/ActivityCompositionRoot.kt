package br.com.mathsemilio.simpleapodbrowser.common.di

import androidx.appcompat.app.AppCompatActivity
import br.com.mathsemilio.simpleapodbrowser.data.repository.APoDRepository
import br.com.mathsemilio.simpleapodbrowser.storage.database.FavoriteAPoDDatabase
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.FragmentContainerHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.ViewFactory

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val compositionRoot: CompositionRoot
) {
    private val retrofit get() = compositionRoot.retrofitBuilder.retroFit

    private val aPoDApi get() = compositionRoot.retrofitBuilder.apodApi

    private val favoriteAPoDDatabase get() = FavoriteAPoDDatabase.getDatabase(activity)

    private val favoriteAPoDDAO get() = favoriteAPoDDatabase.favoriteApodDAO

    val eventPoster get() = compositionRoot.eventPoster

    private val _screensNavigator by lazy {
        ScreensNavigator(
            eventPoster,
            activity.supportFragmentManager,
            activity as FragmentContainerHelper
        )
    }
    val screensNavigator get() = _screensNavigator

    private val _viewFactory by lazy {
        ViewFactory(activity.layoutInflater)
    }
    val viewFactory get() = _viewFactory

    private val _apodRepository by lazy {
        APoDRepository(aPoDApi, favoriteAPoDDAO)
    }
    val apodRepository get() = _apodRepository
}