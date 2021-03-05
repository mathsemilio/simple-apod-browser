package br.com.mathsemilio.simpleapodbrowser.domain.usecase

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.common.provider.DispatcherProvider
import br.com.mathsemilio.simpleapodbrowser.data.repository.APoDRepository
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.OperationResult
import kotlinx.coroutines.withContext

class FetchAPoDUseCase(
    private val aPoDRepository: APoDRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseObservable<FetchAPoDUseCase.Listener>() {

    interface Listener {
        fun onFetchAPodUseCaseEvent(result: OperationResult<List<APoD>>)
    }

    suspend fun fetchAPoDsBasedOnDateRange(startDate: String, endDate: String) {
        onFetchAPoDStarted()
        withContext(dispatcherProvider.BACKGROUND) {
            val response = aPoDRepository.getAPoDsBasedOnDateRange(startDate, endDate)
            if (response.isSuccessful)
                withContext(dispatcherProvider.MAIN) {
                    onFetchAPoDsCompleted(response.body()!!)
                }
            else
                withContext(dispatcherProvider.MAIN) {
                    onFetchAPoDsFailed(response.code().toString())
                }
        }
    }

    suspend fun fetchRandomAPoD() {
        onFetchRandomAPoDStarted()
        withContext(dispatcherProvider.BACKGROUND) {
            val response = aPoDRepository.getRandomAPoD(1)
            if (response.isSuccessful)
                withContext(dispatcherProvider.MAIN) {
                    val apod = mutableListOf<APoD>()
                    apod.add(response.body()!!)
                    onFetchRandomAPoDCompleted(apod)
                }
            else
                withContext(dispatcherProvider.MAIN) {
                    onFetchRandomAPoDFailed(response.code().toString())
                }
        }
    }

    private fun onFetchAPoDStarted() {
        listeners.forEach { it.onFetchAPodUseCaseEvent(OperationResult.OnStarted) }
    }

    private fun onFetchRandomAPoDStarted() {
        listeners.forEach { it.onFetchAPodUseCaseEvent(OperationResult.OnStarted) }
    }

    private fun onFetchAPoDsCompleted(apods: List<APoD>) {
        listeners.forEach { it.onFetchAPodUseCaseEvent(OperationResult.OnCompleted(apods)) }
    }

    private fun onFetchRandomAPoDCompleted(apod: List<APoD>) {
        listeners.forEach { it.onFetchAPodUseCaseEvent(OperationResult.OnCompleted(apod)) }
    }

    private fun onFetchAPoDsFailed(errorCode: String) {
        listeners.forEach { it.onFetchAPodUseCaseEvent(OperationResult.OnError(errorCode)) }
    }

    private fun onFetchRandomAPoDFailed(errorCode: String) {
        listeners.forEach { it.onFetchAPodUseCaseEvent(OperationResult.OnError(errorCode)) }
    }
}