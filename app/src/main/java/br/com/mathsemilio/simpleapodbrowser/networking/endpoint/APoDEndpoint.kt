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

import br.com.mathsemilio.simpleapodbrowser.common.performAPICall
import br.com.mathsemilio.simpleapodbrowser.common.provider.APIKeyProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoDSchema
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.networking.APoDApi
import kotlinx.coroutines.Dispatchers

class APoDEndpoint(
    private val aPoDApi: APoDApi,
    private val apiKeyProvider: APIKeyProvider
) {
    suspend fun fetchAPoDsBasedOnDateRange(startDate: String): Result<List<APoDSchema>> {
        return performAPICall(Dispatchers.IO) {
            aPoDApi.getAPoDsBasedOnDateRange(
                apiKeyProvider.getAPoDKey(),
                startDate,
            ).body()?.reversed()!!
        }
    }

    suspend fun fetchAPoDsBasedOnDate(date: String): Result<APoDSchema> {
        return performAPICall(Dispatchers.IO) {
            aPoDApi.getAPoDBasedOnDate(apiKeyProvider.getAPoDKey(), date).body()!!
        }
    }

    suspend fun fetchRandomAPoD(): Result<List<APoDSchema>> {
        return performAPICall(Dispatchers.IO) {
            aPoDApi.getRandomAPoD(apiKeyProvider.getAPoDKey(), 1).body()!!
        }
    }
}