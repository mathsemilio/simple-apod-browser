package br.com.mathsemilio.simpleapodbrowser.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.mathsemilio.simpleapodbrowser.domain.model.CachedAPoD

@Dao
interface APoDCacheDAO {

    @Insert
    fun cacheData(data: List<CachedAPoD>)

    @Update
    fun updateCache(data: List<CachedAPoD>)

    @Query("DELETE FROM cached_apod_table")
    fun clearCache()

    @Query("SELECT * FROM cached_apod_table")
    fun getCachedData(): List<CachedAPoD>
}