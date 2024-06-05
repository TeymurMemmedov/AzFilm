package com.example.azfilm.data

import com.example.azfilm.api.MovieService
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val movieService: MovieService,
    private val movieDao :MovieDao
) {

    val favoriteMovies: Flow<List<FavoriteMovie>> = movieDao.getAllFavoriteMovies()


    // Add a movie to the favorites
    suspend fun addMovieToFavorites(movie: FavoriteMovie) {
        movieDao.insertMovie(movie)
    }

    // Remove a movie from the favorites
    suspend fun removeMovieFromFavorites(movie: FavoriteMovie) {
        movieDao.deleteMovie(movie)
    }


}
