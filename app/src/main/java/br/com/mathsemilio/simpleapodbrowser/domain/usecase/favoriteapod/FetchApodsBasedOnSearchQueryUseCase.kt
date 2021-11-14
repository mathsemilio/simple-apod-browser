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

package br.com.mathsemilio.simpleapodbrowser.domain.usecase.favoriteapod

import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.storage.endpoint.FavoriteApodEndpoint

class FetchApodsBasedOnSearchQueryUseCase(private val endpoint: FavoriteApodEndpoint) {

    sealed class FetchApodBasedOnSearchQueryResult {
        data class Completed(val matchingApods: List<Apod>?) : FetchApodBasedOnSearchQueryResult()
        object Failed : FetchApodBasedOnSearchQueryResult()
    }

    suspend fun fetchApodsBasedOn(searchQuery: String): FetchApodBasedOnSearchQueryResult {
        var fetchApodBasedOnSearchQueryResult: FetchApodBasedOnSearchQueryResult

        endpoint.fetchFavoriteApodsBasedOn(searchQuery).also { result ->
            fetchApodBasedOnSearchQueryResult = when (result) {
                is Result.Completed ->
                    FetchApodBasedOnSearchQueryResult.Completed(matchingApods = result.data)
                is Result.Failed ->
                    FetchApodBasedOnSearchQueryResult.Failed
            }
        }

        return fetchApodBasedOnSearchQueryResult
    }
}