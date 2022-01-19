package com.example.seriesfollower.data.models.apimodels

import android.util.Log
import com.example.seriesfollower.GeneralConstants
import com.example.seriesfollower.data.utils.NetworkResult
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
            Log.e(GeneralConstants.LOG_TAG, e.message.toString())
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error(GeneralConstants.API_CALL_FAILED + errorMessage)
}