package br.com.mathsemilio.simpleapodbrowser.domain.endpoint

import br.com.mathsemilio.simpleapodbrowser.common.provider.APIKeyProvider
import br.com.mathsemilio.simpleapodbrowser.common.provider.DispatcherProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoDSchema
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.networking.APoDApi
import kotlinx.coroutines.withContext

class APoDEndpoint(
    private val aPoDApi: APoDApi,
    private val apiKeyProvider: APIKeyProvider,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun getAPoDsBasedOnDateRange(startDate: String): Result<List<APoDSchema>> {
        return withContext(dispatcherProvider.BACKGROUND) {
            val response = aPoDApi.getAPoDsBasedOnDateRange(apiKeyProvider.getAPoDKey(), startDate)
            if (response.isSuccessful) {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Completed(data = response.body()?.reversed())
                }
            } else {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Failed(error = response.code().toString())
                }
            }
        }
    }

    suspend fun getAPoDsBasedOnDate(date: String): Result<APoDSchema> {
        return withContext(dispatcherProvider.BACKGROUND) {
            val response = aPoDApi.getAPoDBasedOnDate(apiKeyProvider.getAPoDKey(), date)
            if (response.isSuccessful) {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Completed(data = response.body())
                }
            } else {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Failed(error = response.code().toString())
                }
            }
        }
    }

    suspend fun getRandomAPoD(): Result<List<APoDSchema>> {
        return withContext(dispatcherProvider.BACKGROUND) {
            val response = aPoDApi.getRandomAPoD(apiKeyProvider.getAPoDKey(), 1)
            if (response.isSuccessful) {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Completed(data = response.body())
                }
            } else {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Failed(error = response.code().toString())
                }
            }
        }
    }
}