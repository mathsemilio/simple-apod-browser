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

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.common.util.formatTimeInMillis
import br.com.mathsemilio.simpleapodbrowser.common.util.toApod
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.networking.endpoint.ApodEndpoint

class FetchApodBasedOnDateUseCase(
    private val endpoint: ApodEndpoint
) : BaseObservable<FetchApodBasedOnDateUseCase.Listener>() {

    interface Listener {
        fun onFetchApodBasedOnDateCompleted(apod: Apod)

        fun onFetchApodBasedOnDateFailed()
    }

    suspend fun getApodBasedOnDate(dateInMillis: Long) {
        endpoint.getApodsBasedOnDate(dateInMillis.formatTimeInMillis()).also { result ->
            when (result) {
                is Result.Completed -> notifyListener { listener ->
                    listener.onFetchApodBasedOnDateCompleted(apod = result.data?.toApod()!!)
                }
                is Result.Failed -> notifyListener { listener ->
                    listener.onFetchApodBasedOnDateFailed()
                }
            }
        }
    }
}