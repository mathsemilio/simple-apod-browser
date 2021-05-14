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

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteApodEndpoint

class AddFavoriteApodUseCase(private val endpoint: FavoriteApodEndpoint) :
    BaseObservable<AddFavoriteApodUseCase.Listener>() {

    interface Listener {
        fun onAddApodToFavoritesCompleted()

        fun onApodIsAlreadyOnFavorites()

        fun onAddApodToFavoritesFailed()
    }

    suspend fun addApodToFavorites(apod: Apod) {
        if (isApodAlreadyOnFavorites(apod)) {
            listeners.forEach { listener ->
                listener.onApodIsAlreadyOnFavorites()
            }
            return
        }

        endpoint.insert(apod.copy(isFavorite = true)).also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onAddApodToFavoritesCompleted()
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onAddApodToFavoritesFailed()
                }
            }
        }
    }

    private suspend fun isApodAlreadyOnFavorites(currentApod: Apod): Boolean {
        var isAlreadyOnFavorites = false

        endpoint.getFavoriteApodByDate(currentApod.date).also { result ->
            when (result) {
                is Result.Completed -> {
                    isAlreadyOnFavorites = result.data != null
                }
                is Result.Failed -> throw RuntimeException(result.error)
            }
        }

        return isAlreadyOnFavorites
    }
}