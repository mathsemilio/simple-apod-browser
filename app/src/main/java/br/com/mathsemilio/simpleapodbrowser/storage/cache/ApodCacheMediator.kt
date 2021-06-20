package br.com.mathsemilio.simpleapodbrowser.storage.cache

import br.com.mathsemilio.simpleapodbrowser.data.dao.CachedApodDao
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.networking.ApodApi

class ApodCacheMediator(
    private val cachedApodDao: CachedApodDao,
    private val apodApi: ApodApi
) {
    suspend fun cacheData(apod: Apod) : Result<Nothing>? {
        return null
    }

    suspend fun clearCachedData(): Result<Nothing>? {
        return null
    }

    suspend fun retrieveCachedData(): Result<List<Apod>>? {
        return null
    }
}