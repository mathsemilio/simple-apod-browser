package br.com.mathsemilio.simpleapodbrowser.storage.cache

import br.com.mathsemilio.simpleapodbrowser.common.observable.BaseObservable
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod

class CachedApodRetriever : BaseObservable<CachedApodRetriever.Listener>() {

    interface Listener {
        fun onCachedDataRetrievedSuccessfully(cachedData: List<Apod>)

        fun onRetrieveCachedDataFailed()

        fun onCachedDataClearedSuccessfully()

        fun onClearCachedDataFailed()

        fun onCachedDataUpdatedSuccessfully()

        fun onUpdateCachedDataFailed()
    }

    suspend fun cacheData(apod: Apod) {}

    suspend fun clearCachedData() {}

    suspend fun retrieveCachedData(): List<Apod> {
        return emptyList()
    }
}