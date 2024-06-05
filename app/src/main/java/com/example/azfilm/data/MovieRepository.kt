package com.example.azfilm.data

import com.example.azfilm.api.MovieService
import com.example.azfilm.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class MovieRepository(
    private val movieService: MovieService,
    private val movieDao :MovieDao
) {

    val favoriteMovies: Flow<List<FavoriteMovie>> = movieDao.getAllFavoriteMovies()

    suspend fun getRecents(page:Int = 1) = safeApiCall(Dispatchers.IO) { movieService.getRecents(page) }
    suspend fun getClassics(page:Int = 1) = safeApiCall(Dispatchers.IO) { movieService.getClassics(page) }
    suspend fun getModerns(page:Int = 1) =safeApiCall(Dispatchers.IO) { movieService.getModerns(page) }
    suspend fun getAnimations(page:Int = 1) = safeApiCall(Dispatchers.IO) { movieService.getAnimations(page) }

    suspend fun getMovieById(id:Int) = movieService.getMovieById(id)

    // Add a movie to the favorites
    suspend fun addMovieToFavorites(movie: FavoriteMovie) {
        movieDao.insertMovie(movie)
    }

    // Remove a movie from the favorites
    suspend fun removeMovieFromFavorites(movie: FavoriteMovie) {
        movieDao.deleteMovie(movie)
    }


    fun isFavoriteMovie(movieId:Int): Boolean = runBlocking {
        val favoriteList = favoriteMovies.firstOrNull() // Collect the first list emitted
        favoriteList?.any{
            it.id == movieId
        } ?: false
    }




}
