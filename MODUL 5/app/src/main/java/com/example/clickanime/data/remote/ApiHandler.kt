package com.example.clickanime.data.remote

import com.example.clickanime.data.remote.model.ApiResponse
import retrofit2.Response
import timber.log.Timber

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResponse<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) ApiResponse.Success(body)
            else ApiResponse.Error(response.code(), "Response body is null")
        } else {
            val errorMsg = response.errorBody()?.string() ?: "Unknown error"
            Timber.e("API Error [${response.code()}]: $errorMsg")
            ApiResponse.Error(response.code(), errorMsg)
        }
    } catch (e: Exception) {
        Timber.e(e, "API Exception")
        ApiResponse.Exception(e)
    }
}