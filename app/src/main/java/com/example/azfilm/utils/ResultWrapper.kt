package com.example.azfilm.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed class ResultWrapper<out T> {

    data object Loading : ResultWrapper<Nothing>()

    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: String? = null) :
        ResultWrapper<Nothing>()

    data object NetworkError : ResultWrapper<Nothing>()

}

sealed class ResultWrapperLocal<out T> {
    data class Success<out T>(val value: T) : ResultWrapperLocal<T>()
    data class Error(val error: String? = null) :
        ResultWrapperLocal<Nothing>()

}

sealed class AuthResultWrapper<out T> {

    data object Loading : AuthResultWrapper<Nothing>()

    data class Success<out T>(val value: T) : AuthResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: String? = null) :
        AuthResultWrapper<Nothing>()

    data object NetworkError : AuthResultWrapper<Nothing>()

    data object Logout : AuthResultWrapper<Nothing>()

}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    ResultWrapper.NetworkError
                }

                is HttpException -> {
                    val code = throwable.code()
                    val errorMessage = throwable.message()
                    ResultWrapper.GenericError(code, errorMessage)
                }

                is Exception->{
                    ResultWrapper.GenericError(null,throwable.message)
                }

                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }
}


suspend fun <T> safeAuthRequest(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): AuthResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            AuthResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    AuthResultWrapper.NetworkError
                }

                is HttpException -> {
                    val code = throwable.code()
                    val errorMessage = throwable.message()
                    AuthResultWrapper.GenericError(code, errorMessage)
                }

                is Exception->{
                    AuthResultWrapper.GenericError(null,throwable.message)
                }

                else -> {
                    AuthResultWrapper.GenericError(null, null)
                }
            }
        }
    }
}