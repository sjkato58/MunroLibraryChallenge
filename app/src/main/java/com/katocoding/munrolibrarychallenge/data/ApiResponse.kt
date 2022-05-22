package com.katocoding.munrolibrarychallenge.data

import com.katocoding.munrolibrarychallenge.data.errors.ErrorType

sealed class ApiResponse<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T): ApiResponse<T>(data)
    class Error<T>(message: String, errorType: ErrorType, data: T? = null): ApiResponse<T>(data, message)
    class Loading<T>(data: T? = null): ApiResponse<T>(data)
}