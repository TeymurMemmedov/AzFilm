package com.example.azfilm.ui.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.azfilm.data.FavoriteMovie
import com.example.azfilm.data.MovieRepository
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _favoriteMovies = MutableLiveData<List<FavoriteMovie>>()
    val favoriteMovies: LiveData<List<FavoriteMovie>> = _favoriteMovies


    private var favoritesListener: ValueEventListener? = null

    fun loadFavorites() {

        movieRepository.getFavoriteMovies { favorites ->
            _favoriteMovies.postValue(favorites)
        }
    }

    fun clearFavorites() {
        _favoriteMovies.value = emptyList()
        movieRepository.removeFavoritesListener()
    }

    fun addMovieToFavorites(movie: FavoriteMovie) {
        movieRepository.addMovieToFavorites(movie)
    }

    fun removeMovieFromFavorites(movie: FavoriteMovie) {
        movieRepository.removeMovieFromFavorites(movie)
    }

    override fun onCleared() {
        super.onCleared()
        clearFavorites()
    }
}


//
//import android.util.Log
//import androidx.lifecycle.*
//import com.example.azfilm.data.FavoriteMovie
//import com.example.azfilm.data.MovieRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class FavoritesViewModel @Inject constructor(
//    private val movieRepository: MovieRepository
//) : ViewModel() {
//
//    var favoriteMovies:  LiveData<List<FavoriteMovie>> =
//        movieRepository.favoriteMovies.asLiveData()
//
//
//    fun isMovieFavorite(movieId:Int): Boolean? {
//        return favoriteMovies.value?.any {
//            it.id == movieId
//        }
//    }
//
//    // Add a movie to the favorites
//    fun addMovieToFavorites(movie: FavoriteMovie) {
//        Log.d("ADD_MOVIE_TO_FAVORITE","Add Movie To Favorites worked")
//        viewModelScope.launch {
//            movieRepository.addMovieToFavorites(movie)
//        }
//    }
//
//    // Remove a movie from the favorites
//    fun removeMovieFromFavorites(movie: FavoriteMovie) {
//        Log.d("REMOVE_FROM_FAVORITES","Remove from Favorites worked")
//        viewModelScope.launch {
//            movieRepository.removeMovieFromFavorites(movie)
//        }
//    }
//
//
//}
//
//
