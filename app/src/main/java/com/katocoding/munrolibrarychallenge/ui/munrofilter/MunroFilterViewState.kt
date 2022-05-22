package com.katocoding.munrolibrarychallenge.ui.munrofilter

import com.katocoding.munrolibrarychallenge.DEFAULT_DOUBLE
import com.katocoding.munrolibrarychallenge.data.errors.ErrorType
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.FILTER_DEFAULT
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.HillCategoryType
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.SortType

data class MunroFilterViewState(
    /*var hillCategory: HillCategoryType = HillCategoryType.Either,
    var sortHeightMType: SortType = SortType.Inactive,
    var sortAlphabetType: SortType = SortType.Inactive,
    var sortLimit: Int = FILTER_DEFAULT,
    var maxHeight: Double = DEFAULT_DOUBLE,
    var minHeight: Double = DEFAULT_DOUBLE,*/
    val showError: Boolean = false,
    val maxError: ErrorType = ErrorType.NONE,
    val minError: ErrorType = ErrorType.NONE
)