package com.example.azfilm.ui.movie

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.azfilm.api.serviceModels.MovieDetailsResponseItem
import com.example.azfilm.api.serviceModels.MovieResponseItem
import com.example.azfilm.data.MovieRepository
import com.example.azfilm.data.mapper.mapMovieDetailUIModelToFavoriteMovie
import com.example.azfilm.data.mapper.mapMovieDetailsResponseItemToMovieDetailUIModel
import com.example.azfilm.ui.uiModels.MovieDetailUIModel
import com.example.azfilm.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    val movieRepository: MovieRepository) : ViewModel() {

    private val _selectedFilm = MutableLiveData<ResultWrapper<Response<MovieDetailsResponseItem>>?>()
    val selectedFilm: LiveData<ResultWrapper<Response<MovieDetailsResponseItem>>?> = _selectedFilm


    private val _selectedFilmUIModel = MutableLiveData<MovieDetailUIModel?>()
    val selectedFilmUIModel:LiveData<MovieDetailUIModel?> = _selectedFilmUIModel


    fun setSelectedFilmUIModel(movieResponseItem: MovieDetailsResponseItem?)
    {
        if (movieResponseItem != null) {

            val movieUIModel = mapMovieDetailsResponseItemToMovieDetailUIModel(movieResponseItem)
            movieUIModel.isFavorite = movieRepository.isFavoriteMovie(movieUIModel.id)
            _selectedFilmUIModel.postValue(movieUIModel)
        }

    }


//
//    fun <T> LiveData<T>.distinctUntilChanged(): LiveData<T> {
//        val mediator = MediatorLiveData<T>()
//        mediator.addSource(this) { value ->
//            if (mediator.value != value) {
//                mediator.value = value
//            }
//        }
//        return mediator
//    }

    fun clearSelectedFilmData() {
        if(_selectedFilm.value!=null && _selectedFilmUIModel.value!=null) {
            _selectedFilm.value = null
            _selectedFilmUIModel.value = null
        }
    }



    fun changeFavoritStateOfSelectedFilm(){
        val currentSelectedFilm = _selectedFilmUIModel.value
        val currentState = currentSelectedFilm?.isFavorite

        if (currentState != null) {
            val newState = currentState.not()
            if(newState){

                viewModelScope.launch {
                    _selectedFilmUIModel.value?.let {
                        mapMovieDetailUIModelToFavoriteMovie(
                            it
                        )
                    }?.let {
                        Log.d("ADD_MOVIE_TO_FAVORITE","Add Movie To Favorites worked")
                        movieRepository.addMovieToFavorites(it) }
                }
            }
            else{


                viewModelScope.launch {
                    _selectedFilmUIModel.value?.let {
                        mapMovieDetailUIModelToFavoriteMovie(
                            it
                        )
                    }?.let {
                        Log.d("REMOVE_FROM_FAVORITES","Remove from Favorites worked")
                        movieRepository.removeMovieFromFavorites(it) }
                }

            }
            _selectedFilmUIModel.postValue(
               currentSelectedFilm.copy(
                    isFavorite =  currentState.not()
                )
            )
        }
    }



     fun getMovieById(id: Int) {
         _selectedFilm.postValue(ResultWrapper.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                val response =  movieRepository.getMovieById(id)
                _selectedFilm.postValue(response)
        }
    }
}

