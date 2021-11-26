package com.abbasi.data.utils

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal object NetworkUtils {

    private const val genericNetworkError = "An error occurred getting data from server."
    private const val HTTP_CODE_UNAUTHORIZED = 401
    private const val HTTP_CODE_TIME_OUT = 408
    private const val HTTP_CODE_SERVER_ERROR = 500

    fun getNetworkErrorMessage(e: Exception) = when (e) {
        is IOException -> "Network not available."
        is SocketTimeoutException -> "Request timed out. Try again."
        is UnknownHostException -> "Unable to connect to server."
        else -> genericNetworkError
    }

    fun getErrorMessage(httpCode: Int) = when (httpCode) {
        // User unauthorised error
        HTTP_CODE_UNAUTHORIZED -> "You have been unauthorized."
        // Time out error
        HTTP_CODE_TIME_OUT -> "Request timed out. Try again."
        // Internal server error
        HTTP_CODE_SERVER_ERROR -> "A server error occurred."
        // Any other error executing the API
        else -> genericNetworkError
    }
}