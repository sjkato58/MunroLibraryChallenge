package com.katocoding.munrolibrarychallenge.data.munrolist.filter

enum class SortType(val label: String) {
    Ascending("Ascending"), Descending("Descending"), Inactive("");

    companion object {
        fun from(findValue: String): SortType = SortType.values().first { it.label == findValue }
    }

    override fun toString(): String {
        return label
    }
}