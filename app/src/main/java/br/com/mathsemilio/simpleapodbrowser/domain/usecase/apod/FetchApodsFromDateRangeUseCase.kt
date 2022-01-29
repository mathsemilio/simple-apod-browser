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

import br.com.mathsemilio.simpleapodbrowser.domain.model.*
import br.com.mathsemilio.simpleapodbrowser.common.util.date.*
import br.com.mathsemilio.simpleapodbrowser.common.util.converter.toApodList
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.ApodEndpoint
import br.com.mathsemilio.simpleapodbrowser.common.DEFAULT_DATE_RANGE_LAST_SEVEN_DAYS

class FetchApodsFromDateRangeUseCase(private val endpoint: ApodEndpoint) {

    sealed class FetchApodsFromDateRangeResult {
        data class Completed(val apods: List<Apod>) : FetchApodsFromDateRangeResult()

        object Failed : FetchApodsFromDateRangeResult()
    }

    suspend fun fetchFrom(preferenceRange: String?): FetchApodsFromDateRangeResult {
        var result: FetchApodsFromDateRangeResult

        val formattedDateRange = getDateRangeFrom(
            preferenceRange ?: DEFAULT_DATE_RANGE_LAST_SEVEN_DAYS
        ).formatRange()

        endpoint.fetchApodsFromDateRange(formattedDateRange).also { endpointResult ->
            result = when (endpointResult) {
                is Result.Completed ->
                    FetchApodsFromDateRangeResult.Completed(endpointResult.data?.toApodList() ?: emptyList())
                is Result.Failed ->
                    FetchApodsFromDateRangeResult.Failed
            }
        }

        return result
    }
}
