package com.example.azfilm.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.azfilm.api.serviceModels.MovieResponseItem
import com.example.azfilm.base.BasePagingResponse
import com.example.azfilm.data.MovieRepository
import com.example.azfilm.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {


    private val _searchResults =
        MutableLiveData<ResultWrapper<BasePagingResponse<MovieResponseItem>>>()

    val searchResults:LiveData<ResultWrapper<BasePagingResponse<MovieResponseItem>>> =
        _searchResults

    fun fetchSearchResults(
        query: String
    ){
        _searchResults.postValue(ResultWrapper.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Search_Process","started")
            val searchResults = movieRepository.getSearchResults(query)
            _searchResults.postValue(searchResults)
            Log.d("Search_Process","ended")
        }
    }
}