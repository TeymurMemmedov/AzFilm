package com.example.azfilm.ui.models

import com.example.azfilm.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.security.AccessController.getContext

object RetrofitInstance {

    val client = OkHttpClient.Builder()
        .addInterceptor(CustomInterceptor())
        .build()

    private val retrofit = Retrofit.Builder().
            baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val api = retrofit.create(MovieService::class.java)
}