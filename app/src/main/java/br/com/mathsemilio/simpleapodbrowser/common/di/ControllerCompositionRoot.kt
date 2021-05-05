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

    val aPoDImageExporter get() = activityCompositionRoot.aPoDImageExporter

    val messagesManager get() = activityCompositionRoot.messagesManager

    val screensNavigator get() = activityCompositionRoot.screensNavigator

    val statusBarManager get() = activityCompositionRoot.statusBarManager

    val systemUIManager get() = activityCompositionRoot.systemUIManager

    val tapGestureHelper get() = activityCompositionRoot.tapGestureHelper

    val rootLayoutHelper get() = activityCompositionRoot.hostLayoutHelper

    val permissionsHelper get() = activityCompositionRoot.permissionsHelper

    val viewFactory get() = activityCompositionRoot.viewFactory

    val fetchAPoDUseCase get() = activityCompositionRoot.fetchAPoDUseCase

    val fetchRandomAPoDUseCase get() = activityCompositionRoot.fetchRandomAPoDUseCase

    val fetchAPoDBasedOnDateUseCase get() = activityCompositionRoot.fetchAPoDBasedOnDateUseCase

    val fetchFavoriteAPoDUseCase get() = activityCompositionRoot.fetchFavoriteAPoDUseCase

    val deleteFavoriteAPoDUseCase get() = activityCompositionRoot.deleteFavoriteAPoDUseCase

    val addFavoriteAPodUseCase get() = activityCompositionRoot.addFavoriteAPodUseCase
}