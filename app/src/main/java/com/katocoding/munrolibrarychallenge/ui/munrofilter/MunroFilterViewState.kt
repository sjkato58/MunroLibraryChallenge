package com.katocoding.munrolibrarychallenge.ui.munrofilter

import com.katocoding.munrolibrarychallenge.data.errors.ErrorType

data class MunroFilterViewState(
    val showError: Boolean = false,
    val maxError: ErrorType = ErrorType.NONE
)