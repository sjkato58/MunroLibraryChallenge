package com.katocoding.munrolibrarychallenge.data.munrolist.filter

import com.katocoding.munrolibrarychallenge.DEFAULT_DOUBLE
import com.katocoding.munrolibrarychallenge.data.munrolist.MunroModel
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MunroListFilter @Inject constructor(

) {

    fun checkFilterData(
        filterModel: FilterModel,
        records: List<MunroModel>
    ): List<MunroModel> {
        val hillCategoryFilteredList = filterRecordsByType(FilterByType.HillCategory, filterModel, records)
        val maxHeightFilteredList = filterRecordsByType(FilterByType.MaxHeight, filterModel, hillCategoryFilteredList)
        val minHeightFilteredList = filterRecordsByType(FilterByType.MinHeight, filterModel, maxHeightFilteredList)
        val alphabeticallySortedList = sortRecordsByType(SortByType.Alphabetically, filterModel, minHeightFilteredList)
        val heightSortedList = sortRecordsByType(SortByType.Height, filterModel, alphabeticallySortedList)
        val sortLimitList = filterRecordsByType(FilterByType.SortLimit, filterModel, heightSortedList)
        return sortLimitList
    }

    fun filterRecordsByType(
        filterByType: FilterByType,
        filterModel: FilterModel,
        records: List<MunroModel>
    ): List<MunroModel> = records.mapIndexedNotNull { index, munroModel ->
        when (filterByType) {
            FilterByType.HillCategory -> {
                if ((filterModel.hillCategory == munroModel.hillCategory)
                    || (filterModel.hillCategory == HillCategoryType.Either)) munroModel else null
            }
            FilterByType.MaxHeight -> {
                if ((filterModel.maxHeight >= munroModel.heightM)
                    || (filterModel.maxHeight == DEFAULT_DOUBLE)) munroModel else null
            }
            FilterByType.MinHeight -> {
                if ((filterModel.minHeight <= munroModel.heightM)
                    || (filterModel.minHeight == DEFAULT_DOUBLE)) munroModel else null
            }
            FilterByType.SortLimit -> {
                if (filterModel.sortLimit > index) munroModel else null
            }
        }
    }

    fun sortRecordsByType(
        sortByType: SortByType,
        filterModel: FilterModel,
        records: List<MunroModel>
    ): List<MunroModel> =
        when {
            (sortByType == SortByType.Alphabetically && filterModel.sortAlphabetType == SortType.Ascending) -> {
                records.sortedWith(
                    compareBy(String.CASE_INSENSITIVE_ORDER) { it.name }
                )
            }
            (sortByType == SortByType.Alphabetically && filterModel.sortAlphabetType == SortType.Descending) -> {
                records.sortedWith(
                    compareBy(String.CASE_INSENSITIVE_ORDER) { it.name }
                ).reversed()
            }
            (sortByType == SortByType.Height && filterModel.sortHeightMType == SortType.Ascending) -> {
                records.sortedBy {
                    it.heightM
                }
            }
            (sortByType == SortByType.Height && filterModel.sortHeightMType == SortType.Descending) -> {
                records.sortedByDescending {
                    it.heightM
                }
            }
            else -> records
        }

}

enum class FilterByType {
    HillCategory, MaxHeight, MinHeight, SortLimit
}

enum class SortByType {
    Alphabetically, Height
}