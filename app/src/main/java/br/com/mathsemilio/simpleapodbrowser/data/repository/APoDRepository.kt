package br.com.mathsemilio.simpleapodbrowser.data.repository

import br.com.mathsemilio.simpleapodbrowser.common.provider.APIKeyProvider
import br.com.mathsemilio.simpleapodbrowser.data.dao.FavoriteApodDAO
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.networking.APoDApi

class APoDRepository(
    private val aPoDApi: APoDApi,
    private val favoriteApodDAO: FavoriteApodDAO
) {
    suspend fun getAPoDsBasedOnDateRange(startDate: String) =
        aPoDApi.getAPoDsBasedOnDateRange(APIKeyProvider.getKey(), startDate)

    suspend fun getAPoDBasedOnDate(date: String) =
        aPoDApi.getAPoDBasedOnDate(APIKeyProvider.getKey(), date)

    suspend fun getRandomAPoD(count: Int) =
        aPoDApi.getRandomAPoD(APIKeyProvider.getKey(), count)

    suspend fun addFavoriteApod(favoriteAPoD: FavoriteAPoD) =
        favoriteApodDAO.addFavoriteAPoD(favoriteAPoD)

    suspend fun deleteFavoriteApod(favoriteAPoD: FavoriteAPoD) =
        favoriteApodDAO.deleteFavoriteAPoD(favoriteAPoD)

    suspend fun getFavoriteAPoDs() =
        favoriteApodDAO.getFavoriteAPoDs()

    suspend fun getFavoriteAPoDBasedOnName(name: String) =
        favoriteApodDAO.getFavoriteAPoDBasedOnName(name)
}