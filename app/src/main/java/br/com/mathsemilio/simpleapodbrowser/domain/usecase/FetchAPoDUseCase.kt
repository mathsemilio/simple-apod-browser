package br.com.mathsemilio.simpleapodbrowser.domain.usecase

import br.com.mathsemilio.simpleapodbrowser.common.formatTimeInMillis
import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.domain.endpoint.APoDEndpoint
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result

class FetchAPoDUseCase(private val aPoDEndpoint: APoDEndpoint) :
    BaseObservable<FetchAPoDUseCase.Listener>() {

    interface Listener {
        fun onAPoDFetchCompleted(apods: List<APoD>)
        fun onFetchAPoDBasedOnDateCompleted(apod: APoD)
        fun onFetchRandomAPoDCompleted(randomAPoD: List<APoD>)
        fun onFetchAPoDError(errorCode: String)
    }

    suspend fun fetchAPoDBasedOnDateRange(startDate: String) {
        aPoDEndpoint.getAPoDsBasedOnDateRange(startDate).also { result ->
            when (result) {
                is Result.Completed ->
                    listeners.forEach { it.onAPoDFetchCompleted(result.data!!) }
                is Result.Failed ->
                    listeners.forEach { it.onFetchAPoDError(result.error!!) }
            }
        }
    }

    suspend fun fetchAPoDBasedOnDate(dateInMillis: Long) {
        aPoDEndpoint.getAPoDsBasedOnDate(dateInMillis.formatTimeInMillis()).also { result ->
            when (result) {
                is Result.Completed ->
                    listeners.forEach { it.onFetchAPoDBasedOnDateCompleted(result.data!!) }
                is Result.Failed ->
                    listeners.forEach { it.onFetchAPoDError(result.error!!) }
            }
        }
    }

    suspend fun fetchRandomAPoD() {
        aPoDEndpoint.getRandomAPoD().also { result ->
            when (result) {
                is Result.Completed ->
                    listeners.forEach { it.onFetchRandomAPoDCompleted(result.data!!) }
                is Result.Failed ->
                    listeners.forEach { it.onFetchAPoDError(result.error!!) }
            }
        }
    }
}