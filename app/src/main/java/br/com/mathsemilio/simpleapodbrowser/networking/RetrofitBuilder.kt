package br.com.mathsemilio.simpleapodbrowser.networking

import br.com.mathsemilio.simpleapodbrowser.common.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    private val _retroFit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val _apodApi by lazy {
        _retroFit.create(APoDApi::class.java)
    }
    val apodApi: APoDApi get() = _apodApi
}