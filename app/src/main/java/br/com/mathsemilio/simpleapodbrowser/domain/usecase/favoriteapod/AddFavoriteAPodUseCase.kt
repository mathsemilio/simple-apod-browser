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