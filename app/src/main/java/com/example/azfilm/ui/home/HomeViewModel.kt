package com.example.azfilm.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.azfilm.data.models.DiscoverMovieResponse
import com.example.azfilm.data.models.MovieInfoDetailed
import com.example.azfilm.data.models.MovieInfoMinimalistic
import com.example.azfilm.api.RetrofitInstance
import com.example.azfilm.utils.MovieHelper
import com.example.azfilm.utils.MovieListTypes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel() : ViewModel() {

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
            getMovies(1, MovieListTypes.RECENTS)
            getMovies(1, MovieListTypes.CLASSICS)
            getMovies(1, MovieListTypes.MODERNS)
            getMovies(1, MovieListTypes.ANIMATIONS)
        }


        fun getMovies(page: Int, movieListType: MovieListTypes) {
            val call: Call<DiscoverMovieResponse> = when (movieListType) {
                MovieListTypes.RECENTS -> RetrofitInstance.api.getRecents(page = page)
                MovieListTypes.CLASSICS -> RetrofitInstance.api.getClassics(page = page)
                MovieListTypes.MODERNS -> RetrofitInstance.api.getModerns(page = page)
                MovieListTypes.ANIMATIONS -> RetrofitInstance.api.getAnimations(page = page)
            }

            call.enqueue(object : Callback<DiscoverMovieResponse> {
                override fun onResponse(
                    call: Call<DiscoverMovieResponse>,
                    response: Response<DiscoverMovieResponse>
                ) {
                    response.body()?.let { moviesResponse ->
                        val movies = moviesResponse.results
                        MovieHelper.initializeGenreNames(movies)
                        val resultMovies = movies.filter {
                            !it.poster_path.isNullOrBlank() && !it.backdrop_path.isNullOrBlank()
                        }.shuffled()
                        val currentList = when (movieListType) {
                            MovieListTypes.RECENTS -> _recentFilms.value?.toMutableList() ?: mutableListOf()
                            MovieListTypes.CLASSICS -> _classics.value?.toMutableList() ?: mutableListOf()
                            MovieListTypes.MODERNS -> _moderns.value?.toMutableList() ?: mutableListOf()
                            MovieListTypes.ANIMATIONS -> _animations.value?.toMutableList() ?: mutableListOf()
                        }
                        currentList.addAll(resultMovies)

                        when (movieListType) {
                            MovieListTypes.RECENTS -> {
                                _recentFilms.value = currentList
                            }
                            MovieListTypes.CLASSICS -> {
                                _classics.value = currentList
                            }
                            MovieListTypes.MODERNS -> {
                                _moderns.value = currentList
                            }
                            MovieListTypes.ANIMATIONS -> {
                                _animations.value = currentList
                            }
                        }
                        totalPages[movieListType] = moviesResponse.total_pages
                        currentPage[movieListType] = moviesResponse.page
                    }

                    if (!response.isSuccessful) {
                        Log.d("apiError", "${response.errorBody()}")
                        Log.d("Code", "${response.code()}")
                    }
                }

                override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                    Log.d("NoRespond", "${t.message}")
                }
            })
        }

        fun loadMoreMovies(movieListType: MovieListTypes) {
            val current = currentPage[movieListType] ?: return
            val total = totalPages[movieListType] ?: return
            if (current < total) {
                getMovies(current + 1, movieListType)
            }
        }

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



    private fun logResponseInfo(response: Response<DiscoverMovieResponse>) {
        var headerText = ""
        response.headers().forEach {
            headerText += "${it.first} : ${it.second}\n"
        }
        Log.d("headers", headerText)
        Log.d("message", response.message())
        Log.d("Code", "${response.code()}")
    }
}







