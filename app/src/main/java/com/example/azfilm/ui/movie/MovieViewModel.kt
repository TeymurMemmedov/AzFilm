package com.example.azfilm.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.azfilm.api.RetrofitInstance
import com.example.azfilm.api.serviceModels.MovieDetailsResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel: ViewModel() {

    private val _selectedFilm = MutableLiveData<MovieDetailsResponseItem?>()
    val selectedFilm: LiveData<MovieDetailsResponseItem?> = _selectedFilm

    fun setNullToSelectedFilm() {
        _selectedFilm.value = null
    }

    fun getMovieById(id: Int): MovieDetailsResponseItem? {
        var resultMovie: MovieDetailsResponseItem? = null
        val response = RetrofitInstance.api.getMovieById(id).enqueue(
            object: Callback<MovieDetailsResponseItem> {
                override fun onResponse(
                    p0: Call<MovieDetailsResponseItem>,
                    response: Response<MovieDetailsResponseItem>
                ) {
                    response.body()?.let { moviesResponse ->
                        resultMovie= moviesResponse
                        _selectedFilm.value = resultMovie
                    }

                    if (!response.isSuccessful) {
                        Log.d("apiError", "${response.errorBody()}")
                        Log.d("Code", "${response.code()}")
                    }
                }

                override fun onFailure(p0: Call<MovieDetailsResponseItem>, response: Throwable) {
                    Log.d("NoRespond", "${response.message}")
                }

            }
        )
        return resultMovie

    }
}