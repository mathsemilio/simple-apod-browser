package br.com.mathsemilio.simpleapodbrowser.domain.endpoint

import br.com.mathsemilio.simpleapodbrowser.common.provider.APIKeyProvider
import br.com.mathsemilio.simpleapodbrowser.common.provider.DispatcherProvider
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import br.com.mathsemilio.simpleapodbrowser.networking.APoDApi
import kotlinx.coroutines.withContext

class APoDEndpoint(
    private val aPoDApi: APoDApi,
    private val apiKeyProvider: APIKeyProvider,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun getAPoDsBasedOnDateRange(startDate: String): Result<List<APoD>> {
        return withContext(dispatcherProvider.BACKGROUND) {
            val response = aPoDApi.getAPoDsBasedOnDateRange(apiKeyProvider.getKey(), startDate)
            if (response.isSuccessful) {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Completed(data = response.body()!!)
                }
            } else {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Failed(errorMessage = response.code().toString())
                }
            }
        }
    }

    suspend fun getAPoDsBasedOnDate(date: String): Result<APoD> {
        return withContext(dispatcherProvider.BACKGROUND) {
            val response = aPoDApi.getAPoDBasedOnDate(apiKeyProvider.getKey(), date)
            if (response.isSuccessful) {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Completed(data = response.body()!!)
                }
            } else {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Failed(errorMessage = response.code().toString())
                }
            }
        }
    }

    suspend fun getRandomAPoD(): Result<List<APoD>> {
        return withContext(dispatcherProvider.BACKGROUND) {
            val response = aPoDApi.getRandomAPoD(apiKeyProvider.getKey(), 1)
            if (response.isSuccessful) {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Completed(data = response.body()!!)
                }
            } else {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Failed(errorMessage = response.code().toString())
                }
            }
        }
    }
}