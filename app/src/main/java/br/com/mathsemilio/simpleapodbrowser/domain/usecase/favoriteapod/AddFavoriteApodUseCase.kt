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

import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteApodEndpoint

class AddFavoriteApodUseCase(private val endpoint: FavoriteApodEndpoint) {

    sealed class AddFavoriteApodResult {
        object Completed : AddFavoriteApodResult()

        object AlreadyFavorite : AddFavoriteApodResult()

        object Failed : AddFavoriteApodResult()
    }

    suspend fun addApodToFavorites(apod: Apod): AddFavoriteApodResult {
        var addFavoriteApodResult: AddFavoriteApodResult

        if (isApodAlreadyFavorite(apod)) {
            addFavoriteApodResult = AddFavoriteApodResult.AlreadyFavorite
        } else {
            endpoint.add(apod.copy(isFavorite = true)).also { result ->
                addFavoriteApodResult = when (result) {
                    is Result.Completed -> AddFavoriteApodResult.Completed
                    is Result.Failed -> AddFavoriteApodResult.Failed
                }
            }
        }

        return addFavoriteApodResult
    }

    private suspend fun isApodAlreadyFavorite(apod: Apod): Boolean {
        var isAlreadyFavorite: Boolean

        endpoint.fetchFavoriteApodBy(apod.date).also { result ->
            isAlreadyFavorite = when (result) {
                is Result.Completed -> result.data != null
                is Result.Failed -> throw RuntimeException(result.exception)
            }
        }

        return isAlreadyFavorite
    }
}
