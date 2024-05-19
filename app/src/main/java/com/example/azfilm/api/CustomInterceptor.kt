package com.example.azfilm.api

import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Get the original request
        val originalRequest = chain.request()

        // Add query parameters to the request
        val modifiedUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", "52c8f60847852a53d10858ec3c595bf4")
            .addQueryParameter("with_original_language", "az")
//            .addQueryParameter("language","az-AZ")
            .build()

        // Create a new request with modified URL
        val newRequest = originalRequest.newBuilder()
            .url(modifiedUrl)
            .build()

        // Proceed with the request
        return chain.proceed(newRequest)
    }
}
