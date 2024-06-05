package com.example.azfilm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.azfilm.api.serviceModels.Genre


@Database(entities = [FavoriteMovie::class], version = 1)
abstract class AzFilmDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao


    companion object {
        @Volatile
        private var INSTANCE: AzFilmDatabase? = null
        fun getInstance(context: Context, ): AzFilmDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AzFilmDatabase::class.java,
                    "AzFilmDatabase.db"
                )
//                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }
}