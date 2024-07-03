package com.example.azfilm.data

import androidx.lifecycle.LiveData
import com.example.azfilm.api.MovieService
import com.example.azfilm.data.FavoriteMovie
import com.example.azfilm.data.FirebaseRepository
import com.example.azfilm.data.MovieDao
import com.example.azfilm.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieService: MovieService,
    private val firebaseRepository: FirebaseRepository
) {

    suspend fun getRecents(page: Int = 1) = safeApiCall(Dispatchers.IO) { movieService.getRecents(page) }
    suspend fun getClassics(page: Int = 1) = safeApiCall(Dispatchers.IO) { movieService.getClassics(page) }
    suspend fun getModerns(page: Int = 1) = safeApiCall(Dispatchers.IO) { movieService.getModerns(page) }
    suspend fun getAnimations(page: Int = 1) = safeApiCall(Dispatchers.IO) { movieService.getAnimations(page) }

    suspend fun getSearchResults(query: String, page: Int = 1) =
        safeApiCall(Dispatchers.IO) { movieService.getSearchResults(query, page) }

    suspend fun getMovieById(id: Int) = safeApiCall(Dispatchers.IO) { movieService.getMovieById(id) }

    fun getFavoriteMovies(callback: (List<FavoriteMovie>) -> Unit) {
        firebaseRepository.getFavoriteMovies(callback)
    }

    fun removeFavoritesListener() {
        firebaseRepository.removeFavoritesListener()
    }

    fun addMovieToFavorites(movie: FavoriteMovie) {
        firebaseRepository.addMovieToFavorites(movie)
    }

    fun removeMovieFromFavorites(movie: FavoriteMovie) {
        firebaseRepository.removeMovieFromFavorites(movie)
    }

    fun isFavoriteMovie(movieId: Int, callback: (Boolean) -> Unit) {
        firebaseRepository.isFavoriteMovie(movieId, callback)
    }
}






















//
//import com.example.azfilm.api.MovieService
//import com.example.azfilm.utils.safeApiCall
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.firstOrNull
//import kotlinx.coroutines.runBlocking
//import javax.inject.Inject
//
//class MovieRepository @Inject constructor(
//    private val movieService: MovieService,
//    private val movieDao :MovieDao
//) {
//
//    val favoriteMovies: Flow<List<FavoriteMovie>> = movieDao.getAllFavoriteMovies()
//
//    suspend fun getRecents(page:Int = 1) = safeApiCall(Dispatchers.IO) { movieService.getRecents(page) }
//    suspend fun getClassics(page:Int = 1) = safeApiCall(Dispatchers.IO) { movieService.getClassics(page) }
//    suspend fun getModerns(page:Int = 1) =safeApiCall(Dispatchers.IO) { movieService.getModerns(page) }
//    suspend fun getAnimations(page:Int = 1) = safeApiCall(Dispatchers.IO) { movieService.getAnimations(page) }
//
//    suspend fun getSearchResults( query:String, page: Int = 1,) =
//        safeApiCall(Dispatchers.IO) { movieService.getSearchResults(query,page)}
//
//    suspend fun getMovieById(id:Int) = safeApiCall(Dispatchers.IO){ movieService.getMovieById(id)}
//
//    // Add a movie to the favorites
//    suspend fun addMovieToFavorites(movie: FavoriteMovie) {
//        movieDao.insertMovie(movie)
//    }
//
//    // Remove a movie from the favorites
//    suspend fun removeMovieFromFavorites(movie: FavoriteMovie) {
//        movieDao.deleteMovie(movie)
//    }
//
//
//
//    fun isFavoriteMovie(movieId:Int): Boolean = runBlocking {
//        val favoriteList = favoriteMovies.firstOrNull() // Collect the first list emitted
//        favoriteList?.any{
//            it.id == movieId
//        } ?: false
//    }
//
//    suspend fun deleteAllFavorites() = movieDao.deleteAllFavorites()
//
//
//
//
//}
