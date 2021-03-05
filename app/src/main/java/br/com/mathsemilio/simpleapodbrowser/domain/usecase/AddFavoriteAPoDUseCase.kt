package br.com.mathsemilio.simpleapodbrowser.domain.usecase

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.common.provider.DispatcherProvider
import br.com.mathsemilio.simpleapodbrowser.data.repository.APoDRepository
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.OperationResult
import kotlinx.coroutines.withContext

class AddFavoriteAPoDUseCase(
    private val aPoDRepository: APoDRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseObservable<AddFavoriteAPoDUseCase.Listener>() {

    interface Listener {
        fun onAddFavoriteAPodUseCaseEvent(result: OperationResult<Nothing>)
    }

    suspend fun addFavoriteAPoD(favoriteAPoD: FavoriteAPoD) {
        onAddFavoriteAPoDStarted()
        withContext(dispatcherProvider.BACKGROUND) {
            try {
                aPoDRepository.addFavoriteApod(favoriteAPoD)
                withContext(dispatcherProvider.MAIN) {
                    onAddFavoriteAPoDCompleted()
                }
            } catch (e: Exception) {
                withContext(dispatcherProvider.MAIN) {
                    onAddFavoriteAPoDFailed(e.message!!)
                }
            }
        }
    }

    private fun onAddFavoriteAPoDStarted() {
        listeners.forEach { it.onAddFavoriteAPodUseCaseEvent(OperationResult.OnStarted) }
    }

    private fun onAddFavoriteAPoDCompleted() {
        listeners.forEach { it.onAddFavoriteAPodUseCaseEvent(OperationResult.OnCompleted(null)) }
    }

    private fun onAddFavoriteAPoDFailed(errorMessage: String) {
        listeners.forEach { it.onAddFavoriteAPodUseCaseEvent(OperationResult.OnError(errorMessage)) }
    }
}