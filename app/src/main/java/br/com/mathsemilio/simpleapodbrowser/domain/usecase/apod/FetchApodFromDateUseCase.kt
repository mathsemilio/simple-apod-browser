/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.simpleapodbrowser.domain.usecase.apod

import br.com.mathsemilio.simpleapodbrowser.common.util.converter.toApod
import br.com.mathsemilio.simpleapodbrowser.common.util.date.formatMillisDate
import br.com.mathsemilio.simpleapodbrowser.domain.model.*
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.ApodEndpoint

class FetchApodFromDateUseCase(private val endpoint: ApodEndpoint) {

    sealed class FetchApodFromDateResult {
        data class Completed(val apod: Apod?) : FetchApodFromDateResult()

        object Failed : FetchApodFromDateResult()
    }

    suspend fun fetchFrom(dateInMillis: Long): FetchApodFromDateResult {
        var result: FetchApodFromDateResult

        endpoint.fetchApodFrom(dateInMillis.formatMillisDate()).also { endpointResult ->
            result = when (endpointResult) {
                is Result.Completed ->
                    FetchApodFromDateResult.Completed(endpointResult.data?.toApod())
                is Result.Failed ->
                    FetchApodFromDateResult.Failed
            }
        }

        return result
    }
}
