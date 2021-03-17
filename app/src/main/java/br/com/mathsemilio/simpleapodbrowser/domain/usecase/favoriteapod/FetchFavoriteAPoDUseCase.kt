package br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.domain.endpoint.FavoriteAPoDEndpoint
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result

class FetchFavoriteAPoDUseCase(private val favoriteAPoDEndpoint: FavoriteAPoDEndpoint) :
    BaseObservable<FetchFavoriteAPoDUseCase.Listener>() {

    interface Listener {
        fun onFetchFavoriteAPoDCompleted(favoriteApods: List<FavoriteAPoD>)
        fun onFetchFavoriteAPoDFailed(errorMessage: String)
    }

    suspend fun fetchFavoriteAPoDs() {
        favoriteAPoDEndpoint.getFavoriteAPoDs().also { result ->
            when (result) {
                is Result.Completed ->
                    listeners.forEach { it.onFetchFavoriteAPoDCompleted(result.data!!) }
                is Result.Failed ->
                    listeners.forEach { it.onFetchFavoriteAPoDFailed(result.errorMessage!!) }
            }
        }
    }
}