package br.com.mathsemilio.simpleapodbrowser.storage.endpoint

import br.com.mathsemilio.simpleapodbrowser.common.provider.DispatcherProvider
import br.com.mathsemilio.simpleapodbrowser.data.dao.FavoriteAPoDDAO
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import kotlinx.coroutines.withContext

class FavoriteAPoDEndpoint(
    private val favoriteAPoDDAO: FavoriteAPoDDAO,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun addFavoriteAPoD(apod: APoD): Result<Nothing> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                favoriteAPoDDAO.addFavoriteAPoD(apod)
                return@withContext Result.Completed(data = null)
            } catch (e: Exception) {
                return@withContext Result.Failed(error = null)
            }
        }
    }

    suspend fun deleteFavoriteApoD(apod: APoD): Result<Nothing> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                favoriteAPoDDAO.deleteFavoriteAPoD(apod)
                return@withContext Result.Completed(data = null)
            } catch (e: Exception) {
                return@withContext Result.Failed(error = null)
            }
        }
    }

    suspend fun getFavoriteAPods(): Result<List<APoD>> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                return@withContext Result.Completed(data = favoriteAPoDDAO.getFavoriteAPoDs())
            } catch (e: Exception) {
                return@withContext Result.Failed(error = null)
            }
        }
    }
}