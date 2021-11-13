package br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod

import br.com.mathsemilio.simpleapodbrowser.common.util.toApod
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.ApodEndpoint

class FetchRandomApodUseCase(private val endpoint: ApodEndpoint) {

    sealed class FetchRandomApodResult {
        data class Completed(val randomApod: Apod?) : FetchRandomApodResult()
        object Failed : FetchRandomApodResult()
    }

    suspend fun fetchRandomApod(): FetchRandomApodResult {
        var fetchRandomApodResult: FetchRandomApodResult

        endpoint.fetchRandomApod().also { result ->
            fetchRandomApodResult = when (result) {
                is Result.Completed ->
                    FetchRandomApodResult.Completed(randomApod = result.data?.toApod())
                is Result.Failed ->
                    FetchRandomApodResult.Failed
            }
        }

        return fetchRandomApodResult
    }
}
