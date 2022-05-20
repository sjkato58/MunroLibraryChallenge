package com.katocoding.munrolibrarychallenge.data.munrolist.filter

data class FilterModel(
    var hillCategory: HillCategoryType = HillCategoryType.Either,
    var sortHeightM: SortHeightMType = SortHeightMType.Inactive,
    var sortAlphabetType: SortAlphabetType = SortAlphabetType.Inactive
)