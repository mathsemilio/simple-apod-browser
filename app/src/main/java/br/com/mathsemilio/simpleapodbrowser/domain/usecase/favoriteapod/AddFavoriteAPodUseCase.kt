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
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteAPoDEndpoint

class AddFavoriteAPodUseCase(private val favoriteAPoDEndpoint: FavoriteAPoDEndpoint) :
    BaseObservable<AddFavoriteAPodUseCase.Listener>() {

    interface Listener {
        fun onApoDAddedToFavoritesSuccessfully()

        fun onAPoDIsAlreadyOnFavorites()

        fun onAddAPoDToFavoritesFailed()
    }

    private lateinit var currentAPoD: APoD

    suspend fun addAPoDToFavorites(apod: APoD) {
        currentAPoD = apod

        favoriteAPoDEndpoint.addFavoriteAPoD(apod).also { result ->
            when (result) {
                is Result.Completed -> {
                    if (isAPoDAlreadyOnFavorites())
                        listeners.forEach { listener -> listener.onAPoDIsAlreadyOnFavorites() }
                    else
                        listeners.forEach { listener -> listener.onApoDAddedToFavoritesSuccessfully() }
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onAddAPoDToFavoritesFailed()
                }
            }
        }
    }

    private suspend fun isAPoDAlreadyOnFavorites(): Boolean {
        var isAlreadyOnFavorites = false

        favoriteAPoDEndpoint.getFavoriteAPoDByDate(currentAPoD.date).also { result ->
            when (result) {
                is Result.Completed -> {
                    val favoriteApoD = result.data!!
                    if (favoriteApoD.date == currentAPoD.date)
                        isAlreadyOnFavorites = true
                }
                is Result.Failed -> throw RuntimeException(result.error)
            }
        }

        return isAlreadyOnFavorites
    }
}