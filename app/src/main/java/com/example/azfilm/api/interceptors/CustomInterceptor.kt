package com.example.azfilm.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        // Add query parameters to the request
        val modifiedUrl = originalRequest.url.newBuilder()
            .addQueryParameter("with_original_language", "az")
            .build()

        // Create a new request with modified URL
        val newRequest = originalRequest.newBuilder()
            .url(modifiedUrl)
            .build()

        // Proceed with the request
        return chain.proceed(newRequest)
    }
}
