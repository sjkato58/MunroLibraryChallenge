package com.katocoding.munrolibrarychallenge.ui.munrolist

import com.katocoding.munrolibrarychallenge.DEFAULT_DOUBLE
import com.katocoding.munrolibrarychallenge.DEFAULT_STRING
import com.katocoding.munrolibrarychallenge.data.errors.ErrorType
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.HillCategoryType

data class MunroListViewState(
    val name: String = DEFAULT_STRING,
    val heightM: Double = DEFAULT_DOUBLE,
    val hillCategory: HillCategoryType = HillCategoryType.Munro,
    val gridReference: String = DEFAULT_STRING,
    val showLoading: Boolean = false,
    val showError: Boolean = false,
    val errorType: ErrorType = ErrorType.NONE
)