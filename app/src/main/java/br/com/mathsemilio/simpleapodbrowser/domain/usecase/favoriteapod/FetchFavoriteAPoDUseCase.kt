package br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteAPoDEndpoint

class FetchFavoriteAPoDUseCase(private val favoriteAPoDEndpoint: FavoriteAPoDEndpoint) :
    BaseObservable<FetchFavoriteAPoDUseCase.Listener>() {

    interface Listener {
        fun onFetchFavoriteAPoDCompleted(favoriteApods: List<APoD>)
        fun onFetchFavoriteAPoDFailed()
    }

    suspend fun fetchFavoriteAPods() {
        favoriteAPoDEndpoint.getFavoriteAPods().also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onFetchFavoriteAPoDCompleted(result.data!!)
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onFetchFavoriteAPoDFailed()
                }
            }
        }
    }
}