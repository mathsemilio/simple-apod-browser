package br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.common.util.formatTimeInMillis
import br.com.mathsemilio.simpleapodbrowser.common.util.toApod
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.ApodEndpoint

class FetchApodBasedOnDateUseCase(private val endpoint: ApodEndpoint) :
    BaseObservable<FetchApodBasedOnDateUseCase.Listener>() {

    interface Listener {
        fun onFetchApodBasedOnDateCompleted(apod: Apod)

        fun onFetchApodBasedOnDateFailed(errorMessage: String)
    }

    suspend fun getApodBasedOnDate(dateInMillis: Long) {
        endpoint.getApodsBasedOnDate(dateInMillis.formatTimeInMillis()).also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onFetchApodBasedOnDateCompleted(result.data?.toApod()!!)
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onFetchApodBasedOnDateFailed(result.error!!)
                }
            }
        }
    }
}