package com.example.azfilm.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.azfilm.data.MovieRepository
import com.example.azfilm.data.mapper.mapMovieDetailsResponseItemToMovieDetailUIModel
import com.example.azfilm.ui.uiModels.MovieDetailUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    val movieRepository: MovieRepository) : ViewModel() {

    private val _selectedFilm = MutableLiveData<MovieDetailUIModel?>()
    val selectedFilm: LiveData<MovieDetailUIModel?> = _selectedFilm

    fun setNullToSelectedFilm() {
        _selectedFilm.value = null
    }

    fun changeFavoritStateOfSelectedFilm(){
        val currentSelectedFilm = _selectedFilm.value
        val currentState = currentSelectedFilm?.isFavorite

        if (currentState != null) {
            _selectedFilm.postValue(
               currentSelectedFilm.copy(
                    isFavorite =  currentState.not()
                )

            )
        }
    }

    fun setInitialFavoriteState(){
        val currentFilm = _selectedFilm.value
        if (currentFilm != null) {
            _selectedFilm.postValue(
                currentFilm.copy(
                    isFavorite = movieRepository.isFavoriteMovie(currentFilm.id)
                )
            )
        }
    }



     fun getMovieById(id: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            val response =  movieRepository.getMovieById(id)
            if(response.isSuccessful){
                if(response.body()!=null){
                    val movie = mapMovieDetailsResponseItemToMovieDetailUIModel(
                        response.body()!!
                    )
                    _selectedFilm.postValue(movie)
                }
            }
        }

    }
}

//class MovieViewModelFactory(
//    val movieRepository: MovieRepository
//) : ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return MovieViewModel(movieRepository) as T
//    }
//}