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
import br.com.mathsemilio.simpleapodbrowser.common.util.performAPICall
import br.com.mathsemilio.simpleapodbrowser.domain.model.ApodSchema
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.networking.ApodApi
import kotlinx.coroutines.Dispatchers

class ApodEndpoint(
    private val apodApi: ApodApi,
    private val apiKeyProvider: APIKeyProvider
) {
    suspend fun getApodsBasedOnDateRange(startDate: String): Result<List<ApodSchema>> {
        return performAPICall(Dispatchers.IO) {
            apodApi.getAPoDsBasedOnDateRange(
                apiKeyProvider.getAPoDKey(),
                startDate,
            ).body()?.reversed()!!
        }
    }

    suspend fun getApodsBasedOnDate(date: String): Result<ApodSchema> {
        return performAPICall(Dispatchers.IO) {
            apodApi.getAPoDBasedOnDate(apiKeyProvider.getAPoDKey(), date).body()!!
        }
    }

    suspend fun getRandomApod(): Result<List<ApodSchema>> {
        return performAPICall(Dispatchers.IO) {
            apodApi.getRandomAPoD(apiKeyProvider.getAPoDKey(), 1).body()!!
        }
    }
}