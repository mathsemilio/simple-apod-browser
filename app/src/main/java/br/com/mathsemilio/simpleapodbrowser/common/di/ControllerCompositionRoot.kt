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

import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.*
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod.*
import br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod.*
import br.com.mathsemilio.simpleapodbrowser.ui.common.helper.ImageExporter

class ControllerCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    val dialogManager
        get() = DialogManager(
            activityCompositionRoot.fragmentManager,
            activityCompositionRoot.application
        )

    val eventPublisher
        get() = activityCompositionRoot.eventPublisher

    val eventSubscriber
        get() = activityCompositionRoot.eventSubscriber

    val containerLayoutDelegate
        get() = activityCompositionRoot.containerLayoutDelegate

    val messagesManager
        get() = MessagesManager(activityCompositionRoot.application)

    val imageExporter
        get() = ImageExporter(activityCompositionRoot.application)

    val permissionsHelper
        get() = activityCompositionRoot.permissionsHelper

    val preferencesRepository
        get() = activityCompositionRoot.preferencesRepository

    val systemUIDelegate
        get() = activityCompositionRoot.systemUIDelegate

    val tapGestureHelper
        get() = activityCompositionRoot.tapGestureHelper

    val fetchApodFromDateUseCase
        get() = FetchApodFromDateUseCase(activityCompositionRoot.apodEndpoint)

    val fetchApodsFromDateRangeUseCase
        get() = FetchApodsFromDateRangeUseCase(activityCompositionRoot.apodEndpoint)

    val fetchRandomApodUseCase
        get() = FetchRandomApodUseCase(activityCompositionRoot.apodEndpoint)

    val addFavoriteApodUseCase
        get() = AddApodToFavoritesUseCase(activityCompositionRoot.favoriteApodEndpoint)

    val deleteFavoriteApodUseCase
        get() = DeleteFavoriteApodUseCase(activityCompositionRoot.favoriteApodEndpoint)

    val fetchFavoriteApodsFromSearchQueryUseCase
        get() = FetchFavoriteApodsFromSearchQueryUseCase(activityCompositionRoot.favoriteApodEndpoint)

    val fetchFavoriteApodStatusUseCase
        get() = FetchFavoriteApodStatusUseCase(activityCompositionRoot.favoriteApodEndpoint)

    val fetchFavoriteApodsUseCase
        get() = FetchFavoriteApodsUseCase(activityCompositionRoot.favoriteApodEndpoint)
}
