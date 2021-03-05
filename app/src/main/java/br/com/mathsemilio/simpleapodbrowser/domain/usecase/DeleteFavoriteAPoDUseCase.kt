package br.com.mathsemilio.simpleapodbrowser.domain.usecase

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.common.provider.DispatcherProvider
import br.com.mathsemilio.simpleapodbrowser.data.repository.APoDRepository
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.OperationResult
import kotlinx.coroutines.withContext

class DeleteFavoriteAPoDUseCase(
    private val aPoDRepository: APoDRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseObservable<DeleteFavoriteAPoDUseCase.Listener>() {

    interface Listener {
        fun onDeleteFavoriteAPodUseCaseEvent(result: OperationResult<Nothing>)
    }

    suspend fun deleteFavoriteAPoD(favoriteAPoD: FavoriteAPoD) {
        withContext(dispatcherProvider.BACKGROUND) {
            try {
                aPoDRepository.deleteFavoriteApod(favoriteAPoD)
                withContext(dispatcherProvider.MAIN) {
                    onDeleteFavoriteAPoDCompleted()
                }
            } catch (e: Exception) {
                withContext(dispatcherProvider.MAIN) {
                    onDeleteFavoriteAPoDFailed(e.message!!)
                }
            }
        }
    }

    private fun onDeleteFavoriteAPoDCompleted() {
        listeners.forEach { it.onDeleteFavoriteAPodUseCaseEvent(OperationResult.OnCompleted(null)) }
    }

    private fun onDeleteFavoriteAPoDFailed(errorMessage: String) {
        listeners.forEach { it.onDeleteFavoriteAPodUseCaseEvent(OperationResult.OnError(errorMessage)) }
    }
}