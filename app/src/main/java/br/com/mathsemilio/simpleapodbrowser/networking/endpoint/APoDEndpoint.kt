package br.com.mathsemilio.simpleapodbrowser.networking.endpoint

import br.com.mathsemilio.simpleapodbrowser.common.performAPICall
import br.com.mathsemilio.simpleapodbrowser.common.provider.APIKeyProvider
import br.com.mathsemilio.simpleapodbrowser.common.provider.DispatcherProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoDSchema
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.networking.APoDApi

class APoDEndpoint(
    private val aPoDApi: APoDApi,
    private val apiKeyProvider: APIKeyProvider,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun getAPoDsBasedOnDateRange(startDate: String): Result<List<APoDSchema>> {
        return performAPICall(dispatcherProvider.BACKGROUND) {
            aPoDApi.getAPoDsBasedOnDateRange(
                apiKeyProvider.getAPoDKey(),
                startDate,
            ).body()?.reversed()!!
        }
    }

    suspend fun getAPoDsBasedOnDate(date: String): Result<APoDSchema> {
        return performAPICall(dispatcherProvider.BACKGROUND) {
            aPoDApi.getAPoDBasedOnDate(apiKeyProvider.getAPoDKey(), date).body()!!
        }
    }

    suspend fun getRandomAPoD(): Result<List<APoDSchema>> {
        return performAPICall(dispatcherProvider.BACKGROUND) {
            aPoDApi.getRandomAPoD(apiKeyProvider.getAPoDKey(), 1).body()!!
        }
    }
}