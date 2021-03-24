package br.com.mathsemilio.simpleapodbrowser.networking

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoDSchema
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APoDApi {

    @GET("apod")
    suspend fun getAPoDsBasedOnDateRange(
        @Query("api_key") key: String,
        @Query("start_date") startDate: String,
        @Query("thumbs") includeThumbnail: Boolean = true
    ): Response<List<APoDSchema>>

    @GET("apod")
    suspend fun getAPoDBasedOnDate(
        @Query("api_key") key: String,
        @Query("date") date: String,
        @Query("thumbs") includeThumbnail: Boolean = true
    ): Response<APoDSchema>

    @GET("apod")
    suspend fun getRandomAPoD(
        @Query("api_key") key: String,
        @Query("count") count: Int,
        @Query("thumbs") includeThumbnail: Boolean = true
    ): Response<List<APoDSchema>>
}