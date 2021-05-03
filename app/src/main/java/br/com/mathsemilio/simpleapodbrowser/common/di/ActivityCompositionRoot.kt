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
import br.com.mathsemilio.simpleapodbrowser.common.provider.DispatcherProvider
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.AddFavoriteAPodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.DeleteFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchFavoriteAPoDUseCase
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.APoDEndpoint
import br.com.mathsemilio.simpleapodbrowser.storage.database.AppDatabase
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteAPoDEndpoint
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.APoDImageExporter
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.HostLayoutHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.PermissionsHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.TapGestureHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.DialogManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.StatusBarManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.SystemUIManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.ViewFactory

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val compositionRoot: CompositionRoot
) {
    private val aPoDApi get() = compositionRoot.retrofitBuilder.apodApi

    private val appDatabase get() = AppDatabase.getDatabase(activity)

    private val favoriteAPoDDAO get() = appDatabase.favoriteAPoDDAO

    private val aPoDEndpoint by lazy {
        APoDEndpoint(aPoDApi, apiKeyProvider, dispatcherProvider)
    }

    private val favoriteAPoDEndpoint by lazy {
        FavoriteAPoDEndpoint(favoriteAPoDDAO, dispatcherProvider)
    }

    private val dispatcherProvider get() = DispatcherProvider

    private val _dialogManager by lazy {
        DialogManager(activity.supportFragmentManager, activity)
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

    private val _aPoDImageExporter by lazy {
        APoDImageExporter(activity)
    }

    private val _messagesManager by lazy {
        MessagesManager(activity)
    }

    private val _screensNavigator by lazy {
        ScreensNavigator(hostLayoutHelper.navHostFragment.findNavController())
    }

    private val _tapGestureHelper by lazy {
        TapGestureHelper()
    }

    private val _permissionsHelper by lazy {
        PermissionsHelper(activity)
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

    val aPoDImageExporter get() = _aPoDImageExporter

    val messagesManager get() = _messagesManager

    val screensNavigator get() = _screensNavigator

    val statusBarManager get() = activity as StatusBarManager

    val systemUIManager get() = activity as SystemUIManager

    val tapGestureHelper get() = _tapGestureHelper

    val permissionsHelper get() = _permissionsHelper

    val viewFactory get() = _viewFactory

    val fetchAPoDUseCase get() = _fetchAPoDUseCase

    val fetchFavoriteAPoDUseCase get() = _fetchFavoriteAPoDUseCase

    val addFavoriteAPodUseCase get() = _addFavoriteAPodUseCase

    val deleteFavoriteAPoDUseCase get() = _deleteFavoriteAPoDUseCase
}