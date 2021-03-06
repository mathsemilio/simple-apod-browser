package br.com.mathsemilio.simpleapodbrowser.domain.usecase

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.common.provider.DispatcherProvider
import br.com.mathsemilio.simpleapodbrowser.data.repository.APoDRepository
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.OperationResult
import kotlinx.coroutines.withContext

class FetchFavoriteAPoDUseCase(
    private val aPoDRepository: APoDRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseObservable<FetchFavoriteAPoDUseCase.Listener>() {

    interface Listener {
        fun onFetchFavoriteAPodUseCaseEvent(result: OperationResult<List<FavoriteAPoD>>)
    }

    suspend fun fetchFavoriteAPoDs() {
        onFetchFavoriteAPoDStarted()
        withContext(dispatcherProvider.BACKGROUND) {
            try {
                val favoriteAPoDs = aPoDRepository.getFavoriteAPoDs()
                withContext(dispatcherProvider.MAIN) {
                    onFetchFavoriteAPoDCompleted(favoriteAPoDs)
                }
            } catch (e: Exception) {
                withContext(dispatcherProvider.MAIN) {
                    onFetchFavoriteAPoDFailed(e.message!!)
                }
            }
        }
    }

    suspend fun fetchFavoriteAPoDBasedOnName(name: String) {
        withContext(dispatcherProvider.BACKGROUND) {
            try {
                val favoriteApodsBasedOnName = aPoDRepository.getFavoriteAPoDBasedOnName(name)
                withContext(dispatcherProvider.MAIN) {
                    onFetchFavoriteAPoDBasedOnNameCompleted(favoriteApodsBasedOnName)
                }
            } catch (e: Exception) {
                withContext(dispatcherProvider.MAIN) {
                    onFetchFavoriteAPoDBasedOnNameFailed(e.message!!)
                }
            }
        }
    }

    private fun onFetchFavoriteAPoDStarted() {
        listeners.forEach {
            it.onFetchFavoriteAPodUseCaseEvent(OperationResult.OnStarted)
        }
    }

    private fun onFetchFavoriteAPoDCompleted(favoriteApods: List<FavoriteAPoD>) {
        listeners.forEach {
            it.onFetchFavoriteAPodUseCaseEvent(OperationResult.OnCompleted(favoriteApods))
        }
    }

    private fun onFetchFavoriteAPoDBasedOnNameCompleted(favoriteApods: List<FavoriteAPoD>) {
        listeners.forEach {
            it.onFetchFavoriteAPodUseCaseEvent(OperationResult.OnCompleted(favoriteApods))
        }
    }

    private fun onFetchFavoriteAPoDFailed(errorMessage: String) {
        listeners.forEach {
            it.onFetchFavoriteAPodUseCaseEvent(OperationResult.OnError(errorMessage))
        }
    }

    private fun onFetchFavoriteAPoDBasedOnNameFailed(errorMessage: String) {
        listeners.forEach {
            it.onFetchFavoriteAPodUseCaseEvent(OperationResult.OnError(errorMessage))
        }
    }
}