package com.example.azfilm.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.azfilm.ui.models.MoviesRepository
import com.example.azfilm.ui.models.MovieInfoMinimalistic
import com.example.azfilm.ui.utils.MovieHelper

class HomeViewModel(private val repository: MoviesRepository) : ViewModel() {


    private val _films = MutableLiveData<List<MovieInfoMinimalistic>>()
    val films: LiveData<List<MovieInfoMinimalistic>> = _films

    fun fetchRecentMovies() {
        repository.fetchRecentMovies(
            onMoviesFetched = { movies ->
                Log.d("films", "${movies.size}")
                _films.value = movies
            },
            onError = { error ->
                Log.d("apiError",error)
            }
        )
    }
}
