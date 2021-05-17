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

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    val coroutineScopeProvider get() = activityCompositionRoot.coroutineScopeProvider

    val eventPublisher get() = activityCompositionRoot.eventPublisher

    val eventSubscriber get() = activityCompositionRoot.eventSubscriber

    val dialogManager get() = activityCompositionRoot.dialogManager

    val apodImageExporter get() = activityCompositionRoot.apodImageExporter

    val messagesManager get() = activityCompositionRoot.messagesManager

    val screensNavigator get() = activityCompositionRoot.screensNavigator

    val snackBarManager get() = activityCompositionRoot.snackBarManager

    val statusBarManager get() = activityCompositionRoot.statusBarManager

    val systemUIManager get() = activityCompositionRoot.systemUIManager

    val tapGestureHelper get() = activityCompositionRoot.tapGestureHelper

    val hostLayoutHelper get() = activityCompositionRoot.hostLayoutHelper

    val permissionsHelper get() = activityCompositionRoot.permissionsHelper

    val preferencesManager get() = activityCompositionRoot.preferencesManager

    val viewFactory get() = activityCompositionRoot.viewFactory

    val fetchApodUseCase get() = activityCompositionRoot.fetchApodUseCase

    val fetchRandomApodUseCase get() = activityCompositionRoot.fetchRandomApodUseCase

    val fetchApodBasedOnDateUseCase get() = activityCompositionRoot.fetchApodBasedOnDateUseCase

    val fetchFavoriteApodUseCase get() = activityCompositionRoot.fetchFavoriteApodUseCase

    val deleteFavoriteApodUseCase get() = activityCompositionRoot.deleteFavoriteApodUseCase

    val addFavoriteApodUseCase get() = activityCompositionRoot.addFavoriteApodUseCase
}