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

package br.com.mathsemilio.simpleapodbrowser.networking.endpoint

import br.com.mathsemilio.simpleapodbrowser.common.provider.APIKeyProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.ApodSchema
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.networking.api.ApodApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApodEndpoint(
    private val apodApi: ApodApi,
    private val apiKeyProvider: APIKeyProvider
) {
    suspend fun fetchApodsBasedOnDateRange(startDate: String): Result<List<ApodSchema>?> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(
                    data = apodApi.fetchApodsBasedOnDateRange(apiKeyProvider.apiKey, startDate)
                        .body()
                        ?.reversed()
                )
            } catch (exception: Exception) {
                Result.Failed(exception = exception)
            }
        }
    }

    suspend fun fetchApodsBasedOn(date: String): Result<ApodSchema?> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(
                    data = apodApi.fetchApodBasedOnDate(apiKeyProvider.apiKey, date).body()
                )
            } catch (exception: Exception) {
                Result.Failed(exception = exception)
            }
        }
    }

    suspend fun fetchRandomApod(): Result<ApodSchema?> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(
                    data = apodApi.fetchRandomApod(apiKeyProvider.apiKey, count = 1).body()?.first()
                )
            } catch (exception: Exception) {
                Result.Failed(exception = exception)
            }
        }
    }
}
