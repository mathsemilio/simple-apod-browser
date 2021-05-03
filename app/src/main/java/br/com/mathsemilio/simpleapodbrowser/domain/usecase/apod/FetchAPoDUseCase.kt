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

import br.com.mathsemilio.simpleapodbrowser.common.formatTimeInMillis
import br.com.mathsemilio.simpleapodbrowser.common.getLastSevenDays
import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.common.toAPoDList
import br.com.mathsemilio.simpleapodbrowser.common.toAPoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.APoDEndpoint

class FetchAPoDUseCase(private val aPoDEndpoint: APoDEndpoint) :
    BaseObservable<FetchAPoDUseCase.Listener>() {

    interface Listener {
        fun onFetchAPoDBasedOnDateRangeCompleted(apods: List<APoD>)
        fun onFetchAPoDBasedOnDateCompleted(apod: APoD)
        fun onFetchRandomAPoDCompleted(randomAPoD: APoD)
        fun onFetchAPoDError(errorMessage: String)
    }

    suspend fun fetchAPoDBasedOnDateRange() {
        aPoDEndpoint.getAPoDsBasedOnDateRange(getLastSevenDays()).also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onFetchAPoDBasedOnDateRangeCompleted(result.data?.toAPoDList()!!)
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onFetchAPoDError(result.error!!)
                }
            }
        }
    }

    suspend fun fetchAPoDBasedOnDate(dateInMillis: Long) {
        aPoDEndpoint.getAPoDsBasedOnDate(dateInMillis.formatTimeInMillis()).also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onFetchAPoDBasedOnDateCompleted(result.data?.toAPoD()!!)
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onFetchAPoDError(result.error!!)
                }
            }
        }
    }

    suspend fun fetchRandomAPoD() {
        aPoDEndpoint.getRandomAPoD().also { result ->
            when (result) {
                is Result.Completed -> listeners.forEach { listener ->
                    listener.onFetchRandomAPoDCompleted(
                        result.data?.toAPoDList()?.first()!!
                    )
                }
                is Result.Failed -> listeners.forEach { listener ->
                    listener.onFetchAPoDError(result.error!!)
                }
            }
        }
    }
}