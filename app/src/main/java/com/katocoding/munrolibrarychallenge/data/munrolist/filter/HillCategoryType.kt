package com.katocoding.munrolibrarychallenge.data.munrolist.filter

enum class HillCategoryType(val label: String) {
    Munro("Munro"), MunroTop("Munro Top"), Either("Either");

    companion object {
        fun from(findValue: String): HillCategoryType = values().first { it.label == findValue }
        fun fromOrNull(findValue: String): HillCategoryType? = values().firstOrNull { it.label == findValue }
    }

    override fun toString(): String {
        return label
    }
}