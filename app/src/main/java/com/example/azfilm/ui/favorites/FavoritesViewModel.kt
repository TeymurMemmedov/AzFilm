package com.example.azfilm.ui.favorites

import androidx.lifecycle.*
import com.example.azfilm.data.FavoriteMovie
import com.example.azfilm.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
//
//    private val _favoriteMovies = MutableLiveData<List<FavoriteMovie>>()
//    val favoriteMovies: LiveData<List<FavoriteMovie>> get() = _favoriteMovies
var favoriteMovies:  LiveData<List<FavoriteMovie>> = movieRepository.favoriteMovies.asLiveData()


    fun isMovieFavorite(movieId:Int): Boolean? {
        return favoriteMovies.value?.any {
            it.id == movieId
        }
    }

    // Add a movie to the favorites
    fun addMovieToFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            movieRepository.addMovieToFavorites(movie)
        }
    }

    // Remove a movie from the favorites
    fun removeMovieFromFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            movieRepository.removeMovieFromFavorites(movie)
        }
    }


}


