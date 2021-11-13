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
import androidx.core.view.GestureDetectorCompat
import br.com.mathsemilio.simpleapodbrowser.common.provider.APIKeyProvider
import br.com.mathsemilio.simpleapodbrowser.common.provider.CoroutineScopeProvider
import br.com.mathsemilio.simpleapodbrowser.data.manager.PreferencesManager
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.ApodEndpoint
import br.com.mathsemilio.simpleapodbrowser.storage.database.FavoriteApodDatabase
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteApodEndpoint
import br.com.mathsemilio.simpleapodbrowser.storage.preferences.PreferencesEndpoint
import br.com.mathsemilio.simpleapodbrowser.ui.common.delegate.ContainerLayoutDelegate
import br.com.mathsemilio.simpleapodbrowser.ui.common.delegate.StatusBarDelegate
import br.com.mathsemilio.simpleapodbrowser.ui.common.delegate.SystemUIDelegate
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.PermissionsHelper
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.TapGestureHelper

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val compositionRoot: CompositionRoot
) {
    private val appDatabase
        get() = FavoriteApodDatabase.getDatabase(application)

    private val favoriteApodDao
        get() = appDatabase.favoriteApodDao

    val tapGestureHelper by lazy {
        TapGestureHelper()
    }

    val permissionsHelper by lazy {
        PermissionsHelper(activity)
    }

    val application
        get() = compositionRoot.application

    val coroutineScopeProvider
        get() = CoroutineScopeProvider

    val containerLayoutDelegate
        get() = activity as ContainerLayoutDelegate

    val eventPublisher
        get() = compositionRoot.eventPublisher

    val eventSubscriber
        get() = compositionRoot.eventSubscriber

    val fragmentManager
        get() = activity.supportFragmentManager

    val gestureDetectorCompat
        get() = GestureDetectorCompat(application, tapGestureHelper)

    val preferencesManager
        get() = PreferencesManager(PreferencesEndpoint(application))

    val statusBarDelegate
        get() = activity as StatusBarDelegate

    val systemUIDelegate
        get() = SystemUIDelegate()

    val apodEndpoint
        get() = ApodEndpoint(compositionRoot.apodApi, APIKeyProvider)

    val favoriteApodEndpoint
        get() = FavoriteApodEndpoint(favoriteApodDao)
}
