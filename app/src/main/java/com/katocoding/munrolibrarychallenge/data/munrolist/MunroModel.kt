package com.katocoding.munrolibrarychallenge.data.munrolist

import com.katocoding.munrolibrarychallenge.DEFAULT_DOUBLE
import com.katocoding.munrolibrarychallenge.DEFAULT_STRING

data class MunroModel(
    val name: String = "",
    val heightM: Double = DEFAULT_DOUBLE,
    val hillCategory: String = DEFAULT_STRING,
    val gridReference: String = DEFAULT_STRING
)