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