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
package br.com.mathsemilio.simpleapodbrowser.networking

import br.com.mathsemilio.simpleapodbrowser.domain.model.ApodSchema
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApi {

    @GET("apod")
    suspend fun getAPoDsBasedOnDateRange(
        @Query("api_key") key: String,
        @Query("start_date") startDate: String,
        @Query("thumbs") includeThumbnail: Boolean = true
    ): Response<List<ApodSchema>>

    @GET("apod")
    suspend fun getAPoDBasedOnDate(
        @Query("api_key") key: String,
        @Query("date") date: String,
        @Query("thumbs") includeThumbnail: Boolean = true
    ): Response<ApodSchema>

    @GET("apod")
    suspend fun getRandomAPoD(
        @Query("api_key") key: String,
        @Query("count") count: Int,
        @Query("thumbs") includeThumbnail: Boolean = true
    ): Response<List<ApodSchema>>
}