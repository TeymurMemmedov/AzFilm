package com.example.azfilm.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FavoriteMovie::class], version = 1)
abstract class AzFilmDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

}