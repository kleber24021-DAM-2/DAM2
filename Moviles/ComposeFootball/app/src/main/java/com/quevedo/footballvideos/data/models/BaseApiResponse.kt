package com.quevedo.footballvideos.data.models

import android.util.Log
import com.quevedo.footballvideos.utils.GeneralConsts
import retrofit2.Response

abstract class BaseApiResponse {
    suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> Response<R>,
        transform: (R) -> T
    ): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(transform(body))
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Log.e(GeneralConsts.ERROR_TAG, e.message.toString(), e)
            return error(e.message ?: e.toString())
        }
    }

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Log.e(GeneralConsts.ERROR_TAG, e.message.toString(), e)
            return error(e.message ?: e.toString())
        }
    }


    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error(GeneralConsts.API_CALL_FAILED + errorMessage)
}