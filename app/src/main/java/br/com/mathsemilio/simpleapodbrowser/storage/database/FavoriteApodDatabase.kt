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

package br.com.mathsemilio.simpleapodbrowser.storage.database

import androidx.room.*
import android.content.Context
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.data.dao.FavoriteApodDao
import br.com.mathsemilio.simpleapodbrowser.common.FAVORITE_APOD_DATABASE

@Database(entities = [Apod::class], version = 1, exportSchema = false)
abstract class FavoriteApodDatabase : RoomDatabase() {

    abstract val favoriteApodDao: FavoriteApodDao

    companion object {
        private val LOCK = Any()

        @Volatile
        private var INSTANCE: FavoriteApodDatabase? = null

        fun getDatabase(context: Context): FavoriteApodDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null)
                return tempInstance

            synchronized(LOCK) {
                val databaseInstance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteApodDatabase::class.java,
                    FAVORITE_APOD_DATABASE
                ).build()

                INSTANCE = databaseInstance
                return INSTANCE!!
            }
        }
    }
}
