package com.katocoding.munrolibrarychallenge.ui.munrofilter

import com.katocoding.munrolibrarychallenge.data.errors.ErrorType

data class MunroFilterResponseViewState(
    val showError: Boolean = false,
    val errorType: ErrorType = ErrorType.UNKNOWN
)