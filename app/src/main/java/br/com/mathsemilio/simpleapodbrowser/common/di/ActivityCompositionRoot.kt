/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package br.com.mathsemilio.simpleapodbrowser.common.di

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import br.com.mathsemilio.simpleapodbrowser.common.provider.CoroutineScopeProvider
import br.com.mathsemilio.simpleapodbrowser.data.manager.PreferencesManager
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchApodBasedOnDateUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchApodsUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchRandomApodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.AddFavoriteApodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.DeleteFavoriteApodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchFavoriteApodUseCase
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.ApodEndpoint
import br.com.mathsemilio.simpleapodbrowser.storage.database.FavoriteApodDatabase
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteApodEndpoint
import br.com.mathsemilio.simpleapodbrowser.storage.preferences.PreferencesEndpoint
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.ApodImageExporter
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.HostLayoutHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.PermissionsHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.TapGestureHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.*
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.ViewFactory

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val compositionRoot: CompositionRoot
) {
    private val apodApi get() = compositionRoot.retrofitBuilder.apodApi

    private val appDatabase get() = FavoriteApodDatabase.getDatabase(activity)

    private val favoriteApodDao get() = appDatabase.favoriteApodDao

    private val apodEndpoint by lazy {
        ApodEndpoint(apodApi, apiKeyProvider)
    }

    private val favoriteApodEndpoint by lazy {
        FavoriteApodEndpoint(favoriteApodDao)
    }

    private val _dialogManager by lazy {
        DialogManager(activity.supportFragmentManager, activity)
    }

    private val _fetchApodUseCase by lazy {
        FetchApodsUseCase(apodEndpoint)
    }

    private val _fetchRandomApodUseCase by lazy {
        FetchRandomApodUseCase(apodEndpoint)
    }

    private val _fetchApodBasedOnDateUseCase by lazy {
        FetchApodBasedOnDateUseCase(apodEndpoint)
    }

    private val _fetchFavoriteApodUseCase by lazy {
        FetchFavoriteApodUseCase(favoriteApodEndpoint)
    }

    private val _addFavoriteApodUseCase by lazy {
        AddFavoriteApodUseCase(favoriteApodEndpoint)
    }

    private val _deleteFavoriteApodUseCase by lazy {
        DeleteFavoriteApodUseCase(favoriteApodEndpoint)
    }

    private val _apodImageExporter by lazy {
        ApodImageExporter(activity)
    }

    private val _messagesManager by lazy {
        MessagesManager(activity)
    }

    private val _screensNavigator by lazy {
        ScreensNavigator(hostLayoutHelper.getNavHostFragment().findNavController())
    }

    private val _snackBarManager by lazy {
        SnackBarManager(activity)
    }

    private val _tapGestureHelper by lazy {
        TapGestureHelper()
    }

    private val _permissionsHelper by lazy {
        PermissionsHelper(activity)
    }

    private val _preferencesManager by lazy {
        PreferencesManager(PreferencesEndpoint(activity))
    }

    private val _viewFactory by lazy {
        ViewFactory(activity.layoutInflater)
    }

    private val apiKeyProvider get() = compositionRoot.apiKeyProvider

    val coroutineScopeProvider get() = CoroutineScopeProvider

    val dialogManager get() = _dialogManager

    val eventPublisher get() = compositionRoot.eventPublisher

    val eventSubscriber get() = compositionRoot.eventSubscriber

    val hostLayoutHelper get() = activity as HostLayoutHelper

    val apodImageExporter get() = _apodImageExporter

    val messagesManager get() = _messagesManager

    val screensNavigator get() = _screensNavigator

    val snackBarManager get() = _snackBarManager

    val statusBarManager get() = activity as StatusBarManager

    val systemUIManager get() = activity as SystemUIManager

    val tapGestureHelper get() = _tapGestureHelper

    val permissionsHelper get() = _permissionsHelper

    val preferencesManager get() = _preferencesManager

    val viewFactory get() = _viewFactory

    val fetchApodUseCase get() = _fetchApodUseCase

    val fetchRandomApodUseCase get() = _fetchRandomApodUseCase

    val fetchApodBasedOnDateUseCase get() = _fetchApodBasedOnDateUseCase

    val fetchFavoriteApodUseCase get() = _fetchFavoriteApodUseCase

    val addFavoriteApodUseCase get() = _addFavoriteApodUseCase

    val deleteFavoriteApodUseCase get() = _deleteFavoriteApodUseCase
}