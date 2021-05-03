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
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteAPoDEndpoint
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result

class AddFavoriteAPodUseCase(private val favoriteAPoDEndpoint: FavoriteAPoDEndpoint) :
    BaseObservable<AddFavoriteAPodUseCase.Listener>() {

    interface Listener {
        fun onApoDAddedToFavoritesCompleted()
        fun onAddAPoDToFavoritesFailed()
    }

    suspend fun addAPoDToFavorites(apod: APoD) {
        favoriteAPoDEndpoint.addFavoriteAPoD(apod).also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onApoDAddedToFavoritesCompleted()
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onAddAPoDToFavoritesFailed()
                }
            }
        }
    }
}