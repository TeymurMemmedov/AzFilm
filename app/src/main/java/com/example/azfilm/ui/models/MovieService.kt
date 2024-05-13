package com.example.azfilm.ui.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    fun getRecentFilms(
        @Query("api_key") apiKey: String,
        @Query("primary_release_date.gte") yearFrom: String,
        @Query("with_original_language") language: String,
        @Query("language") locale: String,
        @Query("page") page: Int,
    ): Call<DiscoverMovieResponse>


}
