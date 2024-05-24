package com.example.azfilm.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.azfilm.data.models.DiscoverMovieResponse
import com.example.azfilm.data.models.MovieInfoDetailed
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import java.time.LocalDateTime

interface MovieService {



    @GET("discover/movie")
    suspend fun getWithoutFiltering(
        @Query("page") page: Int,
    ): Response<DiscoverMovieResponse>


    @GET("discover/movie")
    suspend fun getRecents(
        @Query("page") page:Int,
        @Query("primary_release_year") year:Int = 2024
    ) :Response<DiscoverMovieResponse>


    @GET("discover/movie")
    suspend fun getClassics(
        @Query("page") page: Int,
        @Query("primary_release_date.gte") yearFrom:String = "1898-01-01",
        @Query("primary_release_date.lte") yearTo:String = "1991-12-31"
    ):Response<DiscoverMovieResponse>

    @GET("discover/movie")
    suspend fun getModerns(
        @Query("page") page: Int,
        @Query("primary_release_date.gte") yearFrom:String = "1992-01-01",
        @Query("primary_release_date.lte") yearTo:String = "2023-12-31"
    ):Response<DiscoverMovieResponse>

    @GET("discover/movie")
    suspend fun getAnimations(
        @Query("page") page: Int,
        @Query("with_genres") genre:String = "16"
    ):Response<DiscoverMovieResponse>


    @GET("movie/{id}")
     fun getMovieById(
        @Path("id")id:Long,
//        @Query("append_to_response") appendToResponse:String = "images"
    ): Call<MovieInfoDetailed>

   // https://api.themoviedb.org/3/movie/524321?api_key=52c8f60847852a53d10858ec3c595bf4&append_to_response=credits,images


}
