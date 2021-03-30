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
}