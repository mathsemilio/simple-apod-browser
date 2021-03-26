package br.com.mathsemilio.simpleapodbrowser.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD

@Dao
interface FavoriteAPoDDAO {

    @Insert
    fun addFavoriteAPoD(apod: APoD)

    @Delete
    fun deleteFavoriteAPoD(apod: APoD)

    @Query("SELECT * FROM favorite_apod_table")
    fun getFavoriteAPoDs(): List<APoD>
}