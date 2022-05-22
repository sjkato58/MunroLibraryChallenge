package com.katocoding.munrolibrarychallenge.data

import com.katocoding.munrolibrarychallenge.data.errors.ErrorType

sealed class ApiResponse<T>(val data: T? = null, val errorType: ErrorType? = null) {
    class Success<T>(data: T): ApiResponse<T>(data)
    class Error<T>(errorType: ErrorType, data: T? = null): ApiResponse<T>(data, errorType)
    class Loading<T>(data: T? = null): ApiResponse<T>(data)
}