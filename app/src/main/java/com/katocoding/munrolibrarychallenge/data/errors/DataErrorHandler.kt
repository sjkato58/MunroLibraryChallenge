package com.katocoding.munrolibrarychallenge.data.errors

import android.content.Context
import com.katocoding.munrolibrarychallenge.R

class DataErrorHandler constructor(
    private val context: Context
) {

    fun sortCSVError(
        errorType: ErrorType
    ): String = when (errorType) {
        else -> context.resources.getString(R.string.err_csv_unknown)
    }
}