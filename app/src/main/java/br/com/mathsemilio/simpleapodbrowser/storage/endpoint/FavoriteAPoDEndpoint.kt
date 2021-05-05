/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package br.com.mathsemilio.simpleapodbrowser.storage.endpoint

import br.com.mathsemilio.simpleapodbrowser.data.dao.FavoriteAPoDDAO
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteAPoDEndpoint(private val favoriteAPoDDAO: FavoriteAPoDDAO) {

    suspend fun addFavoriteAPoD(apod: APoD): Result<Nothing> {
        return withContext(Dispatchers.IO) {
            try {
                favoriteAPoDDAO.addFavoriteAPoD(apod)
                Result.Completed(data = null)
            } catch (e: Exception) {
                Result.Failed(error = null)
            }
        }
    }

    suspend fun deleteFavoriteApoD(apod: APoD): Result<Nothing> {
        return withContext(Dispatchers.IO) {
            try {
                favoriteAPoDDAO.deleteFavoriteAPoD(apod)
                Result.Completed(data = null)
            } catch (e: Exception) {
                Result.Failed(error = null)
            }
        }
    }

    suspend fun getFavoriteAPods(): Result<List<APoD>> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(data = favoriteAPoDDAO.getFavoriteAPoDs())
            } catch (e: Exception) {
                Result.Failed(error = null)
            }
        }
    }

    suspend fun getFavoriteAPoDByDate(date: String): Result<APoD> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(data = favoriteAPoDDAO.getFavoriteAPoDByDate(date))
            } catch (e: Exception) {
                Result.Failed(error = e.message)
            }
        }
    }

    suspend fun getFavoriteAPoDsBasedOnSearchQuery(searchQuery: String): Result<List<APoD>> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(
                    data = favoriteAPoDDAO.getFavoriteAPoDsBasedOnSearchQuery(
                        searchQuery
                    ).reversed()
                )
            } catch (e: Exception) {
                Result.Failed(error = null)
            }
        }
    }
}