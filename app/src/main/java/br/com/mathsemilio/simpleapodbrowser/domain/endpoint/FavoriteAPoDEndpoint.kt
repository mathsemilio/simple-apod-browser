package br.com.mathsemilio.simpleapodbrowser.domain.endpoint

import br.com.mathsemilio.simpleapodbrowser.common.provider.DispatcherProvider
import br.com.mathsemilio.simpleapodbrowser.data.dao.FavoriteApodDAO
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import kotlinx.coroutines.withContext

class FavoriteAPoDEndpoint(
    private val favoriteAPoDDAO: FavoriteApodDAO,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun addFavoriteAPoD(favoriteAPoD: FavoriteAPoD): Result<Nothing> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                favoriteAPoDDAO.addFavoriteAPoD(favoriteAPoD)
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Completed(data = null)
                }
            } catch (e: Exception) {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Failed(errorMessage = e.message!!)
                }
            }
        }
    }

    suspend fun deleteFavoriteAPoD(favoriteAPoD: FavoriteAPoD): Result<Nothing> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                favoriteAPoDDAO.deleteFavoriteAPoD(favoriteAPoD)
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Completed(data = null)
                }
            } catch (e: Exception) {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Failed(errorMessage = e.message!!)
                }
            }
        }
    }

    suspend fun getFavoriteAPoDs(): Result<List<FavoriteAPoD>> {
        return withContext(dispatcherProvider.BACKGROUND) {
            try {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Completed(data = favoriteAPoDDAO.getFavoriteAPoDs())
                }
            } catch (e: Exception) {
                return@withContext withContext(dispatcherProvider.MAIN) {
                    Result.Failed(errorMessage = e.message!!)
                }
            }
        }
    }
}