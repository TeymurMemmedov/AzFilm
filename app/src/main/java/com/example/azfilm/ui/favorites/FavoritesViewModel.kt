package com.example.azfilm.ui.favorites

import androidx.lifecycle.*
import com.example.azfilm.data.FavoriteMovie
import com.example.azfilm.data.MovieRepository
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
//
//    private val _favoriteMovies = MutableLiveData<List<FavoriteMovie>>()
//    val favoriteMovies: LiveData<List<FavoriteMovie>> get() = _favoriteMovies
   lateinit var favoriteMovies:  LiveData<List<FavoriteMovie>>

    init {
        favoriteMovies = movieRepository.favoriteMovies.asLiveData()
    }


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

class FavoritesViewModelFactory(
    private val movieRepository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  FavoritesViewModel(movieRepository) as T
    }
}

