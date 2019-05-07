package com.flutlin.apiConfig

import com.google.gson.GsonBuilder
import retrofit2.Response
import java.io.IOException

object ErrorUtil {
    val NO_INTERNET = "No internet connection."
    val SERVER_NOT_RESPONDING = "Server not responding."
    val SERVER_TIMEOUT = "Server timeout error."
    val SOMETHING_WENT_WRONG = "Something has gone wrong."

    fun parseError(response: Response<*>?): ApiError {
        var error: ApiError? = null
        try {
            response?.errorBody()?.let { errorBody ->
                errorBody.string().let { errorString ->
                    val gson = GsonBuilder().create()
                    error = gson.fromJson<ApiError>(errorString, ApiError::class.java)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return error ?: ApiError()
    }

    fun error(response: Response<*>): String {
        val error: ApiError? = parseError(response)
        return error?.message ?: ""
    }
}