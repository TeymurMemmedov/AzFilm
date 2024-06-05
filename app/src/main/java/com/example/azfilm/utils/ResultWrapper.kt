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