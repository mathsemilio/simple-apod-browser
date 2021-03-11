package br.com.mathsemilio.simpleapodbrowser.data.dao

import androidx.room.*
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD

@Dao
interface FavoriteApodDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteAPoD(favoriteAPoD: FavoriteAPoD)

    @Delete
    suspend fun deleteFavoriteAPoD(favoriteAPoD: FavoriteAPoD)

    @Query("SELECT * FROM favorite_apods_table")
    suspend fun getFavoriteAPoDs(): List<FavoriteAPoD>

    @Query("SELECT * FROM favorite_apods_table WHERE title LIKE :name")
    suspend fun getFavoriteAPoDBasedOnName(name: String): List<FavoriteAPoD>
}