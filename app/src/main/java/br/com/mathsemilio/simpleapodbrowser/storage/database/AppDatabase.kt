package br.com.mathsemilio.simpleapodbrowser.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.mathsemilio.simpleapodbrowser.common.APP_DATABASE
import br.com.mathsemilio.simpleapodbrowser.data.dao.FavoriteAPoDDAO
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD

@Database(entities = [APoD::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val favoriteAPoDDAO: FavoriteAPoDDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null)
                return tempInstance

            synchronized(this) {
                val databaseInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    APP_DATABASE
                ).build()

                INSTANCE = databaseInstance
                return INSTANCE!!
            }
        }
    }
}