package br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod

import br.com.mathsemilio.simpleapodbrowser.domain.model.*
import br.com.mathsemilio.simpleapodbrowser.common.util.converter.toApod
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.ApodEndpoint

class FetchRandomApodUseCase(private val endpoint: ApodEndpoint) {

    sealed class FetchRandomApodResult {
        data class Completed(val randomApod: Apod?) : FetchRandomApodResult()

        object Failed : FetchRandomApodResult()
    }

    suspend fun fetchRandomApod(): FetchRandomApodResult {
        var result: FetchRandomApodResult

        endpoint.fetchRandomApod().also { endpointResult ->
            result = when (endpointResult) {
                is Result.Completed ->
                    FetchRandomApodResult.Completed(endpointResult.data?.toApod())
                is Result.Failed ->
                    FetchRandomApodResult.Failed
            }
        }

        return result
    }
}
