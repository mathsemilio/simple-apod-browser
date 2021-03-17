package br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.domain.endpoint.FavoriteAPoDEndpoint

class DeleteFavoriteAPoDUseCase(private val favoriteAPoDEndpoint: FavoriteAPoDEndpoint) :
    BaseObservable<DeleteFavoriteAPoDUseCase.Listener>() {

    interface Listener {
        fun onFavoriteAPoDDeletedSuccessfully()
        fun onFavoriteAPoDDeleteFailed(errorMessage: String)
    }

    suspend fun deleteFavoriteAPoD(favoriteAPoD: FavoriteAPoD) {
        favoriteAPoDEndpoint.deleteFavoriteAPoD(favoriteAPoD).also { result ->
            when (result) {
                is Result.Completed ->
                    listeners.forEach { it.onFavoriteAPoDDeletedSuccessfully() }
                is Result.Failed ->
                    listeners.forEach { it.onFavoriteAPoDDeleteFailed(result.errorMessage!!) }
            }
        }
    }
}