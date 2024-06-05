package com.example.azfilm

import android.app.Application
import com.example.azfilm.api.RetrofitInstance
import com.example.azfilm.data.AzFilmDatabase
import com.example.azfilm.data.MovieRepository

class AzFilmApplication:Application() {

    val database by lazy {
        AzFilmDatabase.getInstance(this)
    }

    val repository by lazy {
        MovieRepository(RetrofitInstance.api,database.movieDao())
    }



}