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

package br.com.mathsemilio.simpleapodbrowser.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod

@Dao
interface FavoriteApodDao {

    @Insert
    suspend fun add(favoriteApod: Apod)

    @Delete
    suspend fun delete(favoriteApod: Apod)

    @Query("SELECT * FROM favorite_apod_table")
    suspend fun fetchFavoriteApods(): List<Apod>

    @Query("SELECT * FROM favorite_apod_table WHERE date = :date")
    suspend fun fetchFavoriteApodFrom(date: String): Apod

    @Query("SELECT * FROM favorite_apod_table WHERE title LIKE '%' || :searchQuery || '%'")
    suspend fun fetchFavoriteApodsFrom(searchQuery: String): List<Apod>
}
