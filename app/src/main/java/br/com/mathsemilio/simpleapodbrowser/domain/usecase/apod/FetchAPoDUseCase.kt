package br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod

import br.com.mathsemilio.simpleapodbrowser.common.formatTimeInMillis
import br.com.mathsemilio.simpleapodbrowser.common.getLastSevenDays
import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.common.schemaToAPoDList
import br.com.mathsemilio.simpleapodbrowser.common.toAPoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.APoDEndpoint

class FetchAPoDUseCase(private val aPoDEndpoint: APoDEndpoint) :
    BaseObservable<FetchAPoDUseCase.Listener>() {

    interface Listener {
        fun onFetchAPoDBasedOnDateRangeCompleted(apods: List<APoD>)
        fun onFetchAPoDBasedOnDateCompleted(apod: APoD)
        fun onFetchRandomAPoDCompleted(randomAPoD: APoD)
        fun onFetchAPoDError(errorMessage: String)
    }

    suspend fun fetchAPoDBasedOnDateRange() {
        aPoDEndpoint.getAPoDsBasedOnDateRange(getLastSevenDays()).also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onFetchAPoDBasedOnDateRangeCompleted(result.data?.schemaToAPoDList()!!)
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onFetchAPoDError(result.error!!)
                }
            }
        }
    }

    suspend fun fetchAPoDBasedOnDate(dateInMillis: Long) {
        aPoDEndpoint.getAPoDsBasedOnDate(dateInMillis.formatTimeInMillis()).also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onFetchAPoDBasedOnDateCompleted(result.data?.toAPoD()!!)
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onFetchAPoDError(result.error!!)
                }
            }
        }
    }

    suspend fun fetchRandomAPoD() {
        aPoDEndpoint.getRandomAPoD().also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onFetchRandomAPoDCompleted(
                        result.data?.schemaToAPoDList()?.first()!!
                    )
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onFetchAPoDError(result.error!!)
                }
            }
        }
    }
}