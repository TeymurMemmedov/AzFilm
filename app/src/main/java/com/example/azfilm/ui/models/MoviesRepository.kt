package com.example.azfilm.ui.models

import com.example.azfilm.ui.utils.MovieHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository(private val movieApiService: MovieService) {
//    val apiKey = "52c8f60847852a53d10858ec3c595bf4"
//    val language = "az"
//    val locale = "az-AZ"
//    fun fetchRecentMovies(onMoviesFetched: (List<MovieInfoMinimalistic>) -> Unit, onError: (String) -> Unit) {
//
//        val yearFrom = "2024-01-01"
//        val page = 1
//        movieApiService.getRecentFilms(apiKey, yearFrom, language, locale, page)
//            .enqueue(object : Callback<DiscoverMovieResponse> {
//                override fun onResponse(
//                    p0: Call<DiscoverMovieResponse>,
//                    p1: Response<DiscoverMovieResponse>
//                ) {
//                    if (p1.isSuccessful) {
//                        val movies = p1.body()?.results
//
//                        if (movies != null) {
//                            MovieHelper.initializeGenreNames(movies)
//                            onMoviesFetched(movies)
//                        } else {
//                            onMoviesFetched(emptyList())
//                        }
//                    } else {
//                        onError("Response not successful")
//                    }
//                }
//                override fun onFailure(p0: Call<DiscoverMovieResponse>, t: Throwable) {
//                    t.message?.let { onError(it) }
//                }
//            })
//    }
}
