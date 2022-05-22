package com.katocoding.munrolibrarychallenge.data.munrolist

import com.katocoding.munrolibrarychallenge.DEFAULT_DOUBLE
import com.katocoding.munrolibrarychallenge.DEFAULT_STRING
import com.katocoding.munrolibrarychallenge.data.MUNRODATA_HILLCATEGORY_MON
import com.katocoding.munrolibrarychallenge.data.MUNRODATA_HILLCATEGORY_TOP
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.HillCategoryType

data class MunroModel(
    val name: String = DEFAULT_STRING,
    val heightM: Double = DEFAULT_DOUBLE,
    val hillCategory: HillCategoryType = HillCategoryType.Munro,
    val gridReference: String = DEFAULT_STRING
)