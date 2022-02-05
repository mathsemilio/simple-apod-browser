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

package br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod

import br.com.mathsemilio.simpleapodbrowser.domain.model.*
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteApodEndpoint

class AddApodToFavoritesUseCase(private val endpoint: FavoriteApodEndpoint) {

    sealed class AddApodToFavoritesResult {
        object Completed : AddApodToFavoritesResult()

        object AlreadyFavorite : AddApodToFavoritesResult()

        object Failed : AddApodToFavoritesResult()
    }

    suspend fun addToFavorites(apod: Apod): AddApodToFavoritesResult {
        var result: AddApodToFavoritesResult

        if (isAlreadyFavorite(apod)) {
            result = AddApodToFavoritesResult.AlreadyFavorite
        } else {
            endpoint.add(apod.copy(isFavorite = true)).also { endpointResult ->
                result = when (endpointResult) {
                    is Result.Completed -> AddApodToFavoritesResult.Completed
                    is Result.Failed -> AddApodToFavoritesResult.Failed
                }
            }
        }

        return result
    }

    private suspend fun isAlreadyFavorite(apod: Apod): Boolean {
        var isAlreadyFavorite: Boolean

        endpoint.fetchFavoriteApodFrom(apod.date).also { endpointResult ->
            isAlreadyFavorite = when (endpointResult) {
                is Result.Completed -> endpointResult.data != null
                is Result.Failed -> throw RuntimeException(endpointResult.exception)
            }
        }

        return isAlreadyFavorite
    }
}
