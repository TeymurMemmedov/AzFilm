package com.example.azfilm.di

import android.content.Context
import androidx.room.Room
import com.example.azfilm.data.AzFilmDatabase
import com.example.azfilm.data.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabaseInstance(
        @ApplicationContext context: Context
    ): AzFilmDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AzFilmDatabase::class.java,
            "AzFilmDatabase.db"
        ).build()
    }

    @Provides
    fun provideMovieDao(database: AzFilmDatabase): MovieDao {
        return database.movieDao()
    }
}