package com.example.azfilm.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.azfilm.data.models.DiscoverMovieResponse
import com.example.azfilm.data.models.MovieInfoDetailed
import com.example.azfilm.data.models.MovieInfoMinimalistic
import com.example.azfilm.api.RetrofitInstance
import com.example.azfilm.data.MovieRepository
import com.example.azfilm.utils.MovieHelper
import com.example.azfilm.utils.MovieListTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(
    private val movieRepository: MovieRepository
) : ViewModel()
{

        private val _recentFilms = MutableLiveData<List<MovieInfoMinimalistic>?>()
        val recentFilms: LiveData<List<MovieInfoMinimalistic>?> = _recentFilms

        private val _classics = MutableLiveData<List<MovieInfoMinimalistic>?>()
        val classics: LiveData<List<MovieInfoMinimalistic>?> = _classics

        private val _moderns = MutableLiveData<List<MovieInfoMinimalistic>?>()
        val moderns: LiveData<List<MovieInfoMinimalistic>?> = _moderns

        private val _animations = MutableLiveData<List<MovieInfoMinimalistic>?>()
        val animations: LiveData<List<MovieInfoMinimalistic>?> = _animations


        private val currentPage = mutableMapOf<MovieListTypes, Int>()
        private val totalPages = mutableMapOf<MovieListTypes, Int>()

        init {
            MovieListTypes.values().forEach { type ->
                currentPage[type] = 1
                totalPages[type] = 1
            }

            viewModelScope.launch(Dispatchers.IO) {
               fetchMovies(1, MovieListTypes.RECENTS)
                fetchMovies(1, MovieListTypes.CLASSICS)
               fetchMovies(1, MovieListTypes.MODERNS)
               fetchMovies(1, MovieListTypes.ANIMATIONS)
                Log.d("mythread", Thread.currentThread().name)
            }
        }




    suspend fun fetchMovies(page: Int, movieListType: MovieListTypes) {

        val response = movieRepository.getMovies(page, movieListType)
        if (response == null) {
            Log.d("NoRespond", "Error",)
        }
        response?.results?.let {
            val movies = it.filter {
                !it.poster_path.isNullOrBlank() && !it.backdrop_path.isNullOrBlank()
            }.shuffled()
            MovieHelper.initializeGenreNames(movies)

            when (movieListType) {
                MovieListTypes.RECENTS -> _recentFilms.postValue(movies)
                MovieListTypes.CLASSICS -> _classics.postValue(movies)
                MovieListTypes.MODERNS -> _moderns.postValue(movies)
                MovieListTypes.ANIMATIONS -> _animations.postValue(movies)
            }
        }


//        return movies
    }




//        suspend fun loadMoreMovies(movieListType: MovieListTypes) {
//            val current = currentPage[movieListType] ?: return
//            val total = totalPages[movieListType] ?: return
//            if (current < total) {
//                fetchMovies(current + 1, movieListType)
//            }
//        }

    private val _selectedFilm = MutableLiveData<MovieInfoDetailed?>()
    val selectedFilm: LiveData<MovieInfoDetailed?> = _selectedFilm

    fun setNullToSelectedFilm() {
        _selectedFilm.value = null
    }

    fun getMovieById(id:Long): MovieInfoDetailed? {
        var resultMovie: MovieInfoDetailed? = null
        val response = RetrofitInstance.api.getMovieById(id).enqueue(
            object:Callback<MovieInfoDetailed>{
                override fun onResponse(
                    p0: Call<MovieInfoDetailed>,
                    response: Response<MovieInfoDetailed>
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

                override fun onFailure(p0: Call<MovieInfoDetailed>, response: Throwable) {
                    Log.d("NoRespond", "${response.message}")
                }

            }
        )
        return resultMovie

    }

}








