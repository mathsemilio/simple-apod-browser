package br.com.mathsemilio.simpleapodbrowser.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.mathsemilio.simpleapodbrowser.domain.model.CachedApod

@Dao
interface CachedApodDao {

    @Insert
    suspend fun insertData(cachedApod: CachedApod)

    @Query("DELETE FROM cached_apod_table")
    suspend fun clearCache()

    @Query("SELECT * FROM cached_apod_table")
    suspend fun getCachedApods(): List<CachedApod>
}