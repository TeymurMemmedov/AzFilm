package com.example.azfilm.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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