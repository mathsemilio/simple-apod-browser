package br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.common.util.toApodList
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.ApodEndpoint

class FetchRandomApodUseCase(
    private val endpoint: ApodEndpoint
) : BaseObservable<FetchRandomApodUseCase.Listener>() {

    interface Listener {
        fun onFetchRandomApodCompleted(randomApod: Apod)

        fun onFetchRandomApodFailed()
    }

    suspend fun getRandomAPoD() {
        endpoint.getRandomApod().also { result ->
            when (result) {
                is Result.Completed -> notifyListener { listener ->
                    listener.onFetchRandomApodCompleted(randomApod = result.data?.toApodList()?.first()!!)
                }
                is Result.Failed -> notifyListener { listener ->
                    listener.onFetchRandomApodFailed()
                }
            }
        }
    }
}