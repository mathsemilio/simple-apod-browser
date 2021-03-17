package br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.common.toFavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.domain.endpoint.FavoriteAPoDEndpoint
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result

class AddFavoriteAPoDUseCase(private val favoriteAPoDEndpoint: FavoriteAPoDEndpoint) :
    BaseObservable<AddFavoriteAPoDUseCase.Listener>() {

    interface Listener {
        fun onFavoriteAPoDAddedSuccessfully()
        fun onFavoriteAPoDAddFailed(errorMessage: String)
    }

    suspend fun addFavoriteAPoD(apod: APoD) {
        favoriteAPoDEndpoint.addFavoriteAPoD(apod.toFavoriteAPoD()).also { result ->
            when (result) {
                is Result.Completed ->
                    listeners.forEach { it.onFavoriteAPoDAddedSuccessfully() }
                is Result.Failed ->
                    listeners.forEach { it.onFavoriteAPoDAddFailed(result.errorMessage!!) }
            }
        }
    }

    suspend fun reAddFavoriteAPoD(favoriteAPoD: FavoriteAPoD) {
        favoriteAPoDEndpoint.addFavoriteAPoD(favoriteAPoD).also { result ->
            when (result) {
                is Result.Completed ->
                    listeners.forEach { it.onFavoriteAPoDAddedSuccessfully() }
                is Result.Failed ->
                    listeners.forEach { it.onFavoriteAPoDAddFailed(result.errorMessage!!) }
            }
        }
    }
}