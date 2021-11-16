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

import br.com.mathsemilio.simpleapodbrowser.common.NO_FAVORITE_APOD_DELETED_EXCEPETION
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteApodEndpoint

class DeleteFavoriteApodUseCase(private val endpoint: FavoriteApodEndpoint) {

    sealed class DeleteFavoriteApodResult {
        object Completed : DeleteFavoriteApodResult()

        object Failed : DeleteFavoriteApodResult()
    }

    sealed class RevertFavoriteApodDeletionResult {
        object Completed : RevertFavoriteApodDeletionResult()

        object Failed : RevertFavoriteApodDeletionResult()
    }

    private var deletedFavoriteApod: Apod? = null

    suspend fun deleteFavoriteApod(favoriteApod: Apod): DeleteFavoriteApodResult {
        var deleteFavoriteApodResult: DeleteFavoriteApodResult

        endpoint.delete(favoriteApod).also { result ->
            deleteFavoriteApodResult = when (result) {
                is Result.Completed -> {
                    deletedFavoriteApod = favoriteApod
                    DeleteFavoriteApodResult.Completed
                }
                is Result.Failed -> DeleteFavoriteApodResult.Failed
            }
        }

        return deleteFavoriteApodResult
    }

    suspend fun revertFavoriteApodDeletion(): RevertFavoriteApodDeletionResult {
        var revertFavoriteApodDeletionResult: RevertFavoriteApodDeletionResult

        if (deletedFavoriteApod == null) {
            throw RuntimeException(NO_FAVORITE_APOD_DELETED_EXCEPETION)
        } else {
            endpoint.add(deletedFavoriteApod ?: throw NullPointerException()).also { result ->
                revertFavoriteApodDeletionResult = when (result) {
                    is Result.Completed -> RevertFavoriteApodDeletionResult.Completed
                    is Result.Failed -> RevertFavoriteApodDeletionResult.Failed
                }
            }
        }

        return revertFavoriteApodDeletionResult
    }
}
