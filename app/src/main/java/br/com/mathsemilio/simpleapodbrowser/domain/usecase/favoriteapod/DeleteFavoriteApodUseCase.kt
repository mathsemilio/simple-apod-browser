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

class DeleteFavoriteApodUseCase(
    private val endpoint: FavoriteApodEndpoint
) : BaseObservable<DeleteFavoriteApodUseCase.Listener>() {

    interface Listener {
        fun onDeleteFavoriteApodCompleted()

        fun onRevertFavoriteApodDeletionCompleted()

        fun onDeleteFavoriteApodFailed()

        fun onRevertFavoriteApodDeletionFailed()
    }

    private lateinit var lastDeletedFavoriteApod: Apod

    suspend fun deleteFavoriteApod(apod: Apod) {
        endpoint.delete(apod).also { result ->
            when (result) {
                is Result.Completed -> notifyListener { listener ->
                    lastDeletedFavoriteApod = apod
                    listener.onDeleteFavoriteApodCompleted()
                }
                is Result.Failed -> notifyListener { listener ->
                    listener.onDeleteFavoriteApodFailed()
                }
            }
        }
    }

    suspend fun revertFavoriteApodDeletion() {
        endpoint.insert(lastDeletedFavoriteApod).also { result ->
            when (result) {
                is Result.Completed -> notifyListener { listener ->
                    listener.onRevertFavoriteApodDeletionCompleted()
                }
                is Result.Failed -> notifyListener { listener ->
                    listener.onRevertFavoriteApodDeletionFailed()
                }
            }
        }
    }
}