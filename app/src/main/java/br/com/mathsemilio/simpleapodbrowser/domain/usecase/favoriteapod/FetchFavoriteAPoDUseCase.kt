package br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteAPoDEndpoint

class FetchFavoriteAPoDUseCase(private val favoriteAPoDEndpoint: FavoriteAPoDEndpoint) :
    BaseObservable<FetchFavoriteAPoDUseCase.Listener>() {

    interface Listener {
        fun onFetchFavoriteAPoDsCompleted(favoriteApods: List<APoD>)
        fun onFetchFavoriteAPoDsBasedOnTitleCompleted(matchingApods: List<APoD>)
        fun onFetchFavoriteAPoDFailed()
    }

    suspend fun fetchFavoriteAPods() {
        favoriteAPoDEndpoint.getFavoriteAPods().also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onFetchFavoriteAPoDsCompleted(result.data!!)
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onFetchFavoriteAPoDFailed()
                }
            }
        }
    }

    suspend fun fetchFavoriteAPoDsBasedOnTitle(searchQuery: String) {
        favoriteAPoDEndpoint.getFavoriteAPoDsBasedOnSearchQuery(searchQuery).also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onFetchFavoriteAPoDsBasedOnTitleCompleted(result.data!!)
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onFetchFavoriteAPoDFailed()
                }
            }
        }
    }
}