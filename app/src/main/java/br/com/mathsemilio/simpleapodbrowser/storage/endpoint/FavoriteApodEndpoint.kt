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

import br.com.mathsemilio.simpleapodbrowser.data.dao.FavoriteApodDao
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteApodEndpoint(private val favoriteApodDao: FavoriteApodDao) {

    suspend fun add(apod: Apod): Result<Nothing> {
        return withContext(Dispatchers.IO) {
            try {
                favoriteApodDao.add(apod)
                Result.Completed(data = null)
            } catch (exception: Exception) {
                Result.Failed(exception)
            }
        }
    }

    suspend fun delete(favoriteApod: Apod): Result<Nothing> {
        return withContext(Dispatchers.IO) {
            try {
                favoriteApodDao.delete(favoriteApod)
                Result.Completed(data = null)
            } catch (exception: Exception) {
                Result.Failed(exception)
            }
        }
    }

    suspend fun fetchFavoriteApods(): Result<List<Apod>> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(favoriteApodDao.fetchFavoriteApods())
            } catch (exception: Exception) {
                Result.Failed(exception)
            }
        }
    }

    suspend fun fetchFavoriteApodFrom(date: String): Result<Apod> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(favoriteApodDao.fetchFavoriteApodFrom(date))
            } catch (exception: Exception) {
                Result.Failed(exception)
            }
        }
    }

    suspend fun fetchFavoriteApodsFrom(searchQuery: String): Result<List<Apod>> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Completed(favoriteApodDao.fetchFavoriteApodsFrom(searchQuery))
            } catch (exception: Exception) {
                Result.Failed(exception)
            }
        }
    }
}
