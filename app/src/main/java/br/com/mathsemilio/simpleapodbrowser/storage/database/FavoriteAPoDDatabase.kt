package br.com.mathsemilio.simpleapodbrowser.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.mathsemilio.simpleapodbrowser.common.FAVORITE_APoDS_DATABASE
import br.com.mathsemilio.simpleapodbrowser.data.dao.FavoriteApodDAO
import br.com.mathsemilio.simpleapodbrowser.domain.model.FavoriteAPoD

@Database(entities = [FavoriteAPoD::class], version = 1, exportSchema = false)
abstract class FavoriteAPoDDatabase : RoomDatabase() {

    abstract val favoriteApodDAO: FavoriteApodDAO

    companion object {
        @Volatile
        private var INSTANCE: FavoriteAPoDDatabase? = null

        fun getDatabase(context: Context): FavoriteAPoDDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null)
                return tempInstance

            synchronized(this) {
                val databaseInstance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteAPoDDatabase::class.java,
                    FAVORITE_APoDS_DATABASE
                ).fallbackToDestructiveMigration().build()

                INSTANCE = databaseInstance
                return INSTANCE!!
            }
        }
    }
}