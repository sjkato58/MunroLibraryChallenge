package com.katocoding.munrolibrarychallenge.data

import com.katocoding.munrolibrarychallenge.data.munrolist.filter.HillCategoryType

fun stringToHillCategoryType(hillCategory: String) = when {
    (hillCategory == MUNRODATA_HILLCATEGORY_TOP) -> HillCategoryType.MunroTop
    (hillCategory == MUNRODATA_HILLCATEGORY_MON) -> HillCategoryType.Munro
    else -> HillCategoryType.Munro
}