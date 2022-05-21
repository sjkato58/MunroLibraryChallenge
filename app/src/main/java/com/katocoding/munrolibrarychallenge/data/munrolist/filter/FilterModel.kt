package com.katocoding.munrolibrarychallenge.data.munrolist.filter

import com.katocoding.munrolibrarychallenge.DEFAULT_DOUBLE


data class FilterModel(
    var hillCategory: HillCategoryType = HillCategoryType.Either,
    var sortHeightMType: SortType = SortType.Inactive,
    var sortAlphabetType: SortType = SortType.Inactive,
    var sortLimit: Int = FILTER_DEFAULT,
    var maxHeight: Double = DEFAULT_DOUBLE,
    var minHeight: Double = DEFAULT_DOUBLE
) {
    fun convertToString() = "${hillCategory.name}{#}${sortHeightMType.name}{#}${sortAlphabetType.name}{#}$sortLimit{#}$maxHeight{#}$minHeight"

    constructor(convertedString: String): this() {
        val divided = convertedString.split("{#}").toTypedArray()
        hillCategory = HillCategoryType.valueOf(divided[0])
        sortHeightMType = SortType.valueOf(divided[1])
        sortAlphabetType = SortType.valueOf(divided[2])
        sortLimit = divided[3].toInt()
        maxHeight = divided[4].toDouble()
        minHeight = divided[5].toDouble()
    }
}