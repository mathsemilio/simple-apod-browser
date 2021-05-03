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

import androidx.room.*
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD

@Dao
interface FavoriteAPoDDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavoriteAPoD(apod: APoD)

    @Delete
    fun deleteFavoriteAPoD(apod: APoD)

    @Query("SELECT * FROM favorite_apod_table")
    fun getFavoriteAPoDs(): List<APoD>

    @Query("SELECT * FROM favorite_apod_table WHERE title LIKE '%' || :searchQuery || '%'")
    fun getFavoriteAPoDsBasedOnSearchQuery(searchQuery: String): List<APoD>
}