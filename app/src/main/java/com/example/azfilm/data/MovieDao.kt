package com.example.azfilm.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: FavoriteMovie): Long

    @Query("SELECT * FROM FavoriteMovie WHERE id = :movieId")
    suspend fun getMovieById(movieId: Long): FavoriteMovie?

    @Query("SELECT * FROM FavoriteMovie")
    fun getAllFavoriteMovies(): Flow<List<FavoriteMovie>>

    @Update
    suspend fun updateMovie(movie: FavoriteMovie)

    @Delete
    suspend fun deleteMovie(movie: FavoriteMovie)

    @Query("DELETE FROM FavoriteMovie WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Long)
}