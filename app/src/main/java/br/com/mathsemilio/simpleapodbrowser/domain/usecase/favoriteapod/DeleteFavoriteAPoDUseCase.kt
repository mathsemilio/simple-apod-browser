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

class DeleteFavoriteAPoDUseCase(private val favoriteAPoDEndpoint: FavoriteAPoDEndpoint) :
    BaseObservable<DeleteFavoriteAPoDUseCase.Listener>() {

    interface Listener {
        fun onFavoriteAPoDDeletedSuccessfully()

        fun onFavoriteAPoDDeleteRevertedSuccessfully()

        fun onDeleteFavoriteAPoDFailed()

        fun onRevertFavoriteAPoDDeletionFailed()
    }

    private lateinit var lastDeletedFavoriteAPoD: APoD

    suspend fun deleteFavoriteAPoD(apod: APoD) {
        favoriteAPoDEndpoint.deleteFavoriteApoD(apod).also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    lastDeletedFavoriteAPoD = apod
                    listener.onFavoriteAPoDDeletedSuccessfully()
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onDeleteFavoriteAPoDFailed()
                }
            }
        }
    }

    suspend fun revertFavoriteAPoDDeletion() {
        favoriteAPoDEndpoint.addFavoriteAPoD(lastDeletedFavoriteAPoD).also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onFavoriteAPoDDeleteRevertedSuccessfully()
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onRevertFavoriteAPoDDeletionFailed()
                }
            }
        }
    }
}