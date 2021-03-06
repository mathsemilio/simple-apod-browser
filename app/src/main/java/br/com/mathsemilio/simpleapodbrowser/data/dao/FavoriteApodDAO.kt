package br.com.mathsemilio.simpleapodbrowser.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD

@Dao
interface FavoriteApodDAO {

    @Insert
    suspend fun addFavoriteAPoD(favoriteAPoD: FavoriteAPoD)

    @Delete
    suspend fun deleteFavoriteAPoD(favoriteAPoD: FavoriteAPoD)

    @Query("SELECT * FROM favorite_apods_table")
    suspend fun getFavoriteAPoDs(): List<FavoriteAPoD>

    @Query("SELECT * FROM favorite_apods_table WHERE title LIKE :name")
    suspend fun getFavoriteAPoDBasedOnName(name: String): List<FavoriteAPoD>
}