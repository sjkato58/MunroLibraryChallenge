package com.katocoding.munrolibrarychallenge.data.munrolist.filter

import com.katocoding.munrolibrarychallenge.DEFAULT_DOUBLE

data class FilterModel(
    var hillCategory: HillCategoryType = HillCategoryType.Either,
    var sortHeightM: SortHeightMType = SortHeightMType.Inactive,
    var sortAlphabetType: SortAlphabetType = SortAlphabetType.Inactive,
    var sortLimit: Int = FILTER_DEFAULT,
    var maxHeight: Double = DEFAULT_DOUBLE,
    var minHeight: Double = DEFAULT_DOUBLE
)