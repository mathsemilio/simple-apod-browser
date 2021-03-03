package br.com.mathsemilio.simpleapodbrowser.networking

import br.com.mathsemilio.simpleapodbrowser.common.API_KEY
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import retrofit2.Response
import retrofit2.http.GET

interface APoDApi {

    @GET("?api_key=$API_KEY")
    suspend fun getAPoD(): Response<List<APoD>>
}