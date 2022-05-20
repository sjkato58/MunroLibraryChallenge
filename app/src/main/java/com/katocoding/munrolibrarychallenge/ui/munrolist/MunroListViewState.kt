package com.katocoding.munrolibrarychallenge.ui.munrolist

data class MunroListViewState(
    val name: String,
    val heightM: String,
    val hillCategory: String,
    val gridReference: String,
    val showLoading: Boolean = false,
    val showError: Boolean = false,
    val errorMessage: String = ""
)