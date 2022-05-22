package com.katocoding.munrolibrarychallenge.data.errors

import com.katocoding.munrolibrarychallenge.R

class DataErrorHandler {

    fun sortCSVError(
        errorType: ErrorType
    ): Int = when (errorType) {
        ErrorType.MaxSmallerThanMin -> R.string.err_max_smaller_min
        else -> R.string.err_csv_unknown
    }
}