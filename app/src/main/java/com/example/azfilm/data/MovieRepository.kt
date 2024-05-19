package com.example.azfilm.data

import android.graphics.Movie
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.azfilm.api.MovieService
import com.example.azfilm.data.models.DiscoverMovieResponse
import com.example.azfilm.data.models.MovieInfoMinimalistic
import com.example.azfilm.utils.MovieHelper
import com.example.azfilm.utils.MovieListTypes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository(
    private val movieService: MovieService
) {
    fun getMovies(page: Int, movieListType: MovieListTypes, onSuccess: (DiscoverMovieResponse) -> Unit, onError: (Throwable) -> Unit) {
        val call: Call<DiscoverMovieResponse> = when (movieListType) {
            MovieListTypes.RECENTS -> movieService.getRecents(page)
            MovieListTypes.CLASSICS -> movieService.getClassics(page)
            MovieListTypes.MODERNS -> movieService.getModerns(page)
            MovieListTypes.ANIMATIONS -> movieService.getAnimations(page)
        }

        call.enqueue(object : Callback<DiscoverMovieResponse> {
            override fun onResponse(
                call: Call<DiscoverMovieResponse>,
                response: Response<DiscoverMovieResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { moviesResponse ->
                        onSuccess(moviesResponse)
                    }
                } else {
                    onError(Throwable(response.errorBody()?.string()))
                }
            }
            override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                onError(t)
            }
        })
    }
}
