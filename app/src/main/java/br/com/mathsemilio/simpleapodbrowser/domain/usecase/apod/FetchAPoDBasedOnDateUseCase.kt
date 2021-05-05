package br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod

import br.com.mathsemilio.simpleapodbrowser.common.formatTimeInMillis
import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.common.toAPoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.APoDEndpoint

class FetchAPoDBasedOnDateUseCase(private val aPoDEndpoint: APoDEndpoint) :
    BaseObservable<FetchAPoDBasedOnDateUseCase.Listener>() {

    interface Listener {
        fun onAPoDBasedOnDateFetchedSuccessfully(apod: APoD)

        fun onFetchAPoDBasedOnDateFailed(errorMessage: String)
    }

    suspend fun fetchAPoDBasedOnDate(dateInMillis: Long) {
        aPoDEndpoint.fetchAPoDsBasedOnDate(dateInMillis.formatTimeInMillis()).also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onAPoDBasedOnDateFetchedSuccessfully(result.data?.toAPoD()!!)
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onFetchAPoDBasedOnDateFailed(result.error!!)
                }
            }
        }
    }
}