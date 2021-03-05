package br.com.mathsemilio.simpleapodbrowser.data.repository

import br.com.mathsemilio.simpleapodbrowser.common.API_KEY
import br.com.mathsemilio.simpleapodbrowser.data.dao.FavoriteApodDAO
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.networking.APoDApi

class APoDRepository(
    private val aPoDApi: APoDApi,
    private val favoriteApodDAO: FavoriteApodDAO
) {
    suspend fun getAPoDsBasedOnDateRange(startDate: String, endDate: String) =
        aPoDApi.getAPoDsBasedOnDateRange(API_KEY, startDate, endDate)

    suspend fun getRandomAPoD(count: Int) = aPoDApi.getRandomAPoD(API_KEY, count)

    suspend fun addFavoriteApod(favoriteAPoD: FavoriteAPoD) =
        favoriteApodDAO.addFavoriteAPoD(favoriteAPoD)

    suspend fun deleteFavoriteApod(favoriteAPoD: FavoriteAPoD) =
        favoriteApodDAO.deleteFavoriteAPoD(favoriteAPoD)

    suspend fun getFavoriteAPoDs() = favoriteApodDAO.getFavoriteAPoDs()
}