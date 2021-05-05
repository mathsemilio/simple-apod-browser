package br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.common.toAPoDList
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.APoDEndpoint

class FetchRandomAPoDUseCase(private val aPoDEndpoint: APoDEndpoint) :
    BaseObservable<FetchRandomAPoDUseCase.Listener>() {

    interface Listener {
        fun onRandomAPoDFetchedSuccessfully(randomAPoD: APoD)

        fun onFetchRandomAPoDFailed(errorMessage: String)
    }

    suspend fun fetchRandomAPoD() {
        aPoDEndpoint.fetchRandomAPoD().also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onRandomAPoDFetchedSuccessfully(result.data?.toAPoDList()?.first()!!)
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onFetchRandomAPoDFailed(result.error!!)
                }
            }
        }
    }
}