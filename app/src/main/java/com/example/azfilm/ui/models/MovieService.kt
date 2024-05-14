package com.example.azfilm.ui.models

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {



    @GET("discover/movie")
    fun getMoviesInAzerbaijani(
        @Query("language") language:String="az-AZ",
        @Query("page") page: Int,
    ): Call<DiscoverMovieResponse>


    @GET("movie/{id}")
    fun getMovieById(
        @Path("id")id:Long,
//        @Query("append_to_response") appendToResponse:String = "images"
    ):Call<MovieInfoDetailed>

   // https://api.themoviedb.org/3/movie/524321?api_key=52c8f60847852a53d10858ec3c595bf4&append_to_response=credits,images


}
