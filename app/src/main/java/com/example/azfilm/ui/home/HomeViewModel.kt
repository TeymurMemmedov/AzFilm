package com.example.azfilm.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.azfilm.api.RetrofitInstance
import com.example.azfilm.api.serviceModels.MovieResponseItem
import com.example.azfilm.base.BasePagingResponse
import com.example.azfilm.data.MovieRepository
import com.example.azfilm.utils.MovieListTypes
import com.example.azfilm.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel()
{
    val movieService = RetrofitInstance.api


    private val _recentFilms = MutableLiveData<ResultWrapper<BasePagingResponse<MovieResponseItem>>>(
            ResultWrapper.Loading
        )
        val recentFilms: LiveData<ResultWrapper<BasePagingResponse<MovieResponseItem>>> = _recentFilms

        private val _classics = MutableLiveData<ResultWrapper<BasePagingResponse<MovieResponseItem>>>(
            ResultWrapper.Loading
        )
        val classics: LiveData<ResultWrapper<BasePagingResponse<MovieResponseItem>>> = _classics

        private val _moderns = MutableLiveData<ResultWrapper<BasePagingResponse<MovieResponseItem>>>(
            ResultWrapper.Loading
        )
        val moderns: LiveData<ResultWrapper<BasePagingResponse<MovieResponseItem>>> = _moderns

        private val _animations = MutableLiveData<ResultWrapper<BasePagingResponse<MovieResponseItem>>>(
            ResultWrapper.Loading
        )
        val animations: LiveData<ResultWrapper<BasePagingResponse<MovieResponseItem>>> = _animations


        private val currentPage = mutableMapOf<MovieListTypes, Int>()
        private val totalPages = mutableMapOf<MovieListTypes, Int>()


    init{
        MovieListTypes.values().forEach { type ->
            currentPage[type] = 0
            totalPages[type] = 0
            }
            getRecents()
            getClassics()
            getModerns()
            getAnimations()

        }

//         suspend private fun getRecents(page:Int = 1) {
//            Log.d("Fetch Status","RecentsStarted")
//            val recents = movieRepository.getRecents(page)
//            _recentFilms.postValue(recents)
//            Log.d("Fetch Status","RecentsEnded")
//
//         }
//
//    suspend private fun getClassics(page:Int = 1) {
//        Log.d("Fetch Status","RecentsStarted")
//        val recents = movieRepository.getClassics(page)
//        _recentFilms.postValue(recents)
//        Log.d("Fetch Status","RecentsEnded")
//
//    }
//
//    suspend private fun getModerns(page:Int = 1) {
//        Log.d("Fetch Status","RecentsStarted")
//        val recents = movieRepository.getModerns(page)
//        _recentFilms.postValue(recents)
//        Log.d("Fetch Status","RecentsEnded")
//
//    }
//
//    suspend private fun getAnimations(page:Int = 1) {
//        Log.d("Fetch Status","RecentsStarted")
//        val recents = movieRepository.getAnimations(page)
//        _recentFilms.postValue(recents)
//        Log.d("Fetch Status","RecentsEnded")
//
//    }




     private fun getRecents(page:Int = 1) {
        viewModelScope.launch {
            Log.d("Fetch Status","RecentsStarted")
            val recents = movieRepository.getRecents(page)
            _recentFilms.postValue(recents)
            Log.d("Fetch Status","RecentsEnded")
        }

    }

     private fun getClassics(page:Int = 1) {
        viewModelScope.launch {
            Log.d("Fetch Status","ClassicsStarted")
            val modern = movieRepository.getClassics(page)
            _classics.postValue(modern)
            Log.d("Fetch Status","ClassicsEnded")
        }

    }

     private fun getModerns(page:Int = 1) {
        viewModelScope.launch {
            Log.d("Fetch Status","ModernsStarted")
            val classics = movieRepository.getModerns(page)
            _moderns.postValue(classics)
            Log.d("Fetch Status","ModernsEnded")
        }

    }

     private fun getAnimations(page:Int = 1) {
        viewModelScope.launch {
            Log.d("Fetch Status","AnimationsStarted")
            val animations = movieRepository.getAnimations(page)
            _animations.postValue(animations)
            Log.d("Fetch Status","AnimationsEnded")
        }

    }


    private fun generateRandomPageNumber(
        movieListTypes: MovieListTypes
    ):Int{

        var randomPageNumber = 0

        do {
             randomPageNumber = (
                    1..totalPages[movieListTypes]!!
                    ).random()

            Log.d("randomPageNumber",randomPageNumber.toString())
        }
        while (randomPageNumber==currentPage[movieListTypes]
            && totalPages[movieListTypes]!! >1)

        return  randomPageNumber

    }

    fun refreshMovieLists(){
        val randomPageNumberForRecents = generateRandomPageNumber(MovieListTypes.RECENTS)
        val randomPageNumberForClassics = generateRandomPageNumber(MovieListTypes.CLASSICS)
        val randomPageNumberForModerns = generateRandomPageNumber(MovieListTypes.MODERNS)
        val randomPageNumberForAnimations = generateRandomPageNumber(MovieListTypes.ANIMATIONS)


        viewModelScope.launch(Dispatchers.IO) {
            getRecents(randomPageNumberForRecents)
            getClassics(randomPageNumberForClassics)
            getModerns(randomPageNumberForModerns)
            getAnimations(randomPageNumberForAnimations)
        }
    }


    fun updateCurrentAndTotalPages(
        result: ResultWrapper<BasePagingResponse<MovieResponseItem>?>,
        movieListTypes: MovieListTypes){

        if(result is ResultWrapper.Success){
                val responseValues = result.value
                if (responseValues != null) {
                    this.currentPage[movieListTypes] = responseValues.page
                    this.totalPages[movieListTypes] = responseValues.total_pages

                    Log.d("current_pages${movieListTypes.name}", currentPage[movieListTypes].toString())
                    Log.d("total_pages${movieListTypes.name}", totalPages[movieListTypes].toString())
                }
        }
    }

}








