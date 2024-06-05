package com.example.azfilm.api

import com.example.azfilm.api.interceptors.APIKeyInterceptor
import com.example.azfilm.api.interceptors.CustomInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }



    val client = OkHttpClient.Builder()
        .addInterceptor(APIKeyInterceptor("api_key","52c8f60847852a53d10858ec3c595bf4"))
        .addInterceptor(loggingInterceptor)
        .addInterceptor(CustomInterceptor())
        .build()

    private val retrofit = Retrofit.Builder().
            baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val api = retrofit.create(MovieService::class.java)
}