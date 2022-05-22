package com.katocoding.munrolibrarychallenge.ui.munrofilter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.katocoding.munrolibrarychallenge.base.BaseViewModel
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

    private val _filterErrorState = MutableLiveData<MunroFilterViewState>()
    val filterErrorState: LiveData<MunroFilterViewState> get() = _filterErrorState

    private val _filterChanged = MutableLiveData<Boolean>().apply {
        postValue(false)
    }
    val filterChanged: LiveData<Boolean> get() = _filterChanged

    fun initFilterModel(newFilterModel: String) {
        _filterModel.postValue(FilterModel(newFilterModel))
    }

    fun updateFilterModel(
        hillCategoryType: String,
        sortHeightMType: String,
        sortAlphabetType: String,
        sortLimit: String,
        maxHeight: String,
        minHeight: String
    ) {
        val filterModel = _filterModel.value
        filterModel?.let {
            it.hillCategory = HillCategoryType.from(hillCategoryType)
            it.sortHeightMType = SortType.from(sortHeightMType)
            it.sortAlphabetType = SortType.from(sortAlphabetType)
            it.sortLimit = sortLimit.toInt()
            it.maxHeight = maxHeight.toDouble()
            it.minHeight = minHeight.toDouble()
        }
        _filterModel.postValue(filterModel)
        _filterChanged.postValue(true)
    }

    fun obtainFilterModel() = _filterModel.value
}