package com.katocoding.munrolibrarychallenge.ui.munrofilter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.katocoding.munrolibrarychallenge.DEFAULT_DOUBLE
import com.katocoding.munrolibrarychallenge.base.BaseViewModel
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.FILTER_DEFAULT
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.FilterModel
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.HillCategoryType
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MunroFilterViewModel @Inject constructor(

): BaseViewModel() {

    private val _filterModel = MutableLiveData<FilterModel>().apply {
        postValue(FilterModel())
    }
    val filterModel: LiveData<FilterModel> get() = _filterModel

    fun initFilterModel(newFilterModel: String) {
        _filterModel.postValue(FilterModel(newFilterModel))
    }

    fun updateFilterModel(
        hillCategoryType: String,
        sortHeightMType: String,
        sortAlphabetType: String,
        sortLimit: String,
        maxHeight: Double,
        minHeight: Double
    ) {
        val filterModel = _filterModel.value
        filterModel?.let {
            it.hillCategory = HillCategoryType.from(hillCategoryType)
            it.sortHeightMType = SortType.from(sortHeightMType)
            it.sortAlphabetType = SortType.from(sortAlphabetType)
            it.sortLimit = sortLimit.toInt()
            it.maxHeight = maxHeight
            it.minHeight = minHeight
        }
        _filterModel.postValue(filterModel)
    }

}