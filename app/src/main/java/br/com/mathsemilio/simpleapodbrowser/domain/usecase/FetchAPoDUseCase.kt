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
        fun onFetchAPoDUseCaseEvent(result: OperationResult<List<APoD>>)
    }

    suspend fun fetchAPoDsBasedOnDateRange(startDate: String) {
        onFetchAPoDStarted()
        withContext(dispatcherProvider.BACKGROUND) {
            val response = aPoDRepository.getAPoDsBasedOnDateRange(startDate)
            if (response.isSuccessful)
                withContext(dispatcherProvider.MAIN) {
                    onFetchAPoDCompleted(response.body()!!)
                }
            else
                withContext(dispatcherProvider.MAIN) {
                    onFetchAPoDFailed(response.code().toString())
                }
        }
    }

    suspend fun fetchAPoDBasedOnDate(date: String) {
        onFetchAPoDStarted()
        withContext(dispatcherProvider.BACKGROUND) {
            val response = aPoDRepository.getAPoDBasedOnDate(date)
            if (response.isSuccessful) {
                val apodList = mutableListOf<APoD>()
                apodList.add(response.body()!!)
                withContext(dispatcherProvider.MAIN) {
                    onFetchAPoDCompleted(apodList)
                }
            } else {
                withContext(dispatcherProvider.MAIN) {
                    onFetchAPoDFailed(response.code().toString())
                }
            }
        }
    }

    suspend fun fetchRandomAPoD() {
        onFetchAPoDStarted()
        withContext(dispatcherProvider.BACKGROUND) {
            val response = aPoDRepository.getRandomAPoD(1)
            if (response.isSuccessful) {
                val apodList = mutableListOf<APoD>()
                apodList.add(response.body()!!)
                withContext(dispatcherProvider.MAIN) {
                    onFetchAPoDCompleted(apodList)
                }
            } else {
                withContext(dispatcherProvider.MAIN) {
                    onFetchAPoDFailed(response.code().toString())
                }
            }
        }
    }

    private fun onFetchAPoDStarted() {
        listeners.forEach { it.onFetchAPoDUseCaseEvent(OperationResult.OnStarted) }
    }

    private fun onFetchAPoDCompleted(apodList: List<APoD>) {
        listeners.forEach { it.onFetchAPoDUseCaseEvent(OperationResult.OnCompleted(apodList)) }
    }

    private fun onFetchAPoDFailed(errorCode: String) {
        listeners.forEach { it.onFetchAPoDUseCaseEvent(OperationResult.OnError(errorCode)) }
    }
}