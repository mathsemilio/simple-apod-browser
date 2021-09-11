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

import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchApodBasedOnDateUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchApodsUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.FetchRandomApodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.AddFavoriteApodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.DeleteFavoriteApodUseCase
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.FetchFavoriteApodUseCase
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.ApodImageExporter
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.DialogManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.MessagesManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.SnackBarManager
import br.com.mathsemilio.simpleapodbrowser.ui.common.navigation.ScreensNavigator

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    private val application get() = activityCompositionRoot.application

    private val apodEndpoint get() = activityCompositionRoot.apodEndpoint

    private val favoriteApodEndpoint get() = activityCompositionRoot.favoriteApodEndpoint

    private val fragmentManager get() = activityCompositionRoot.fragmentManager

    val addFavoriteApodUseCase get() = AddFavoriteApodUseCase(favoriteApodEndpoint)

    val apodImageExporter get() = ApodImageExporter(application)

    val deleteFavoriteApodUseCase get() = DeleteFavoriteApodUseCase(favoriteApodEndpoint)

    val dialogManager get() = DialogManager(fragmentManager, application)

    val coroutineScopeProvider get() = activityCompositionRoot.coroutineScopeProvider

    val eventPublisher get() = activityCompositionRoot.eventPublisher

    val eventSubscriber get() = activityCompositionRoot.eventSubscriber

    val fetchApodUseCase get() = FetchApodsUseCase(apodEndpoint)

    val fetchRandomApodUseCase get() = FetchRandomApodUseCase(apodEndpoint)

    val fetchApodBasedOnDateUseCase get() = FetchApodBasedOnDateUseCase(apodEndpoint)

    val fetchFavoriteApodUseCase get() = FetchFavoriteApodUseCase(favoriteApodEndpoint)

    val hostLayoutHelper get() = activityCompositionRoot.hostLayoutHelper

    val messagesManager get() = MessagesManager(application)

    val permissionsHelper get() = activityCompositionRoot.permissionsHelper

    val preferencesManager get() = activityCompositionRoot.preferencesManager

    val screensNavigator get() = ScreensNavigator(activityCompositionRoot.navController)

    val snackBarManager get() = SnackBarManager(application)

    val statusBarManager get() = activityCompositionRoot.statusBarManager

    val systemUIManager get() = activityCompositionRoot.systemUIManager

    val tapGestureHelper get() = activityCompositionRoot.tapGestureHelper
}