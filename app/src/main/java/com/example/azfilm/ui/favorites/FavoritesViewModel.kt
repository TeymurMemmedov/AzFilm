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
    val favoriteMovies: LiveData<List<FavoriteMovie>> = movieRepository.favoriteMovies.asLiveData()

//    init {
//        loadFavoriteMovies()
//    }

    // Load all favorite movies from the database
//    private fun loadFavoriteMovies() {
//        viewModelScope.launch {
//            val movies = movieRepository.getAllFavoriteMovies()
//            _favoriteMovies.postValue(movies)
//        }
//    }

    fun isMovieFavorite(movieId:Int): Boolean? {
        return favoriteMovies.value?.any {
            it.id == movieId
        }
    }

    // Add a movie to the favorites
    fun addMovieToFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            movieRepository.addMovieToFavorites(movie)
//            loadFavoriteMovies() // Refresh the favorite movies list
        }
    }

    // Remove a movie from the favorites
    fun removeMovieFromFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            movieRepository.removeMovieFromFavorites(movie)
//            loadFavoriteMovies() // Refresh the favorite movies list
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

