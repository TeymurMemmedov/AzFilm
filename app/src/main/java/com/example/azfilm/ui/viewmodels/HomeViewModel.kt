package com.example.azfilm.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.azfilm.ui.models.DiscoverMovieResponse
import com.example.azfilm.ui.models.MovieInfoDetailed
import com.example.azfilm.ui.models.MoviesRepository
import com.example.azfilm.ui.models.MovieInfoMinimalistic
import com.example.azfilm.ui.models.RetrofitInstance
import com.example.azfilm.ui.utils.MovieHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class HomeViewModel() : ViewModel() {


    private val _films = MutableLiveData<List<MovieInfoMinimalistic>?>()
    val films: LiveData<List<MovieInfoMinimalistic>?> = _films


    private val _selectedFilm = MutableLiveData<MovieInfoDetailed?>()
    val selectedFilm: LiveData<MovieInfoDetailed?> = _selectedFilm

    private val _resultPage = MutableLiveData<Int>()
    val resultPage:LiveData<Int> = _resultPage
//
    private val _totalPage = MutableLiveData<Int>()
    val totalPage:LiveData<Int> = _totalPage




    init {
        getMoviesInAzerbaijani(1)
    }

    fun setNullToSelectedFilm(){
        _selectedFilm.value = null
    }

//    fun updateSelectedFilm(id:Long){
//        _selectedFilm.value = getMovieById(id)
//    }


//



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

    fun getMoviesInAzerbaijani(page:Int): List<MovieInfoMinimalistic>? {
        var resultMovies:List<MovieInfoMinimalistic>? = null
        RetrofitInstance.api.getMoviesInAzerbaijani(page = page).enqueue(
            object : Callback<DiscoverMovieResponse> {
                override fun onResponse(
                    call: Call<DiscoverMovieResponse>,
                    response: Response<DiscoverMovieResponse>
                ) {
                    response.body()?.let { moviesResponse ->
                        val movies = moviesResponse.results
                        MovieHelper.initializeGenreNames(movies)
//                        resultMovies = movies.shuffled()
                        resultMovies = movies
                        _films.value  = resultMovies
                        _totalPage.value = moviesResponse.total_pages
                        _resultPage.value = moviesResponse.page

                    }

                    if (!response.isSuccessful) {
                        Log.d("apiError", "${response.errorBody()}")
                        Log.d("Code", "${response.code()}")
                    }

//                    logResponseInfo(response)
                }

                override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                    Log.d("NoRespond", "${t.message}")
                }
            }
        )
        return resultMovies
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
