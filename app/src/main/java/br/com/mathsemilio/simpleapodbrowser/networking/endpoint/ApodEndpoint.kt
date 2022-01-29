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

import kotlinx.coroutines.*
import br.com.mathsemilio.simpleapodbrowser.domain.model.*
import br.com.mathsemilio.simpleapodbrowser.networking.api.ApodApi

class ApodEndpoint(private val apodApi: ApodApi) {

    suspend fun fetchApodsFromDateRange(formattedStartDate: String): Result<List<ApodSchema>?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apodApi.fetchApodsFromDateRange(startDate = formattedStartDate)
                Result.Completed(response.body()?.reversed())
            } catch (exception: Exception) {
                Result.Failed(exception)
            }
        }
    }

    suspend fun fetchApodFrom(formattedDate: String): Result<ApodSchema?> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(apodApi.fetchApodFromDate(date = formattedDate).body())
            } catch (exception: Exception) {
                Result.Failed(exception)
            }
        }
    }

    suspend fun fetchRandomApod(): Result<ApodSchema?> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(apodApi.fetchRandomApod().body()?.first())
            } catch (exception: Exception) {
                Result.Failed(exception)
            }
        }
    }
}
