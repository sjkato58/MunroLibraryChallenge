package com.katocoding.munrolibrarychallenge.ui.munrofilter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.katocoding.munrolibrarychallenge.DEFAULT_DOUBLE
import com.katocoding.munrolibrarychallenge.base.BaseViewModel
import com.katocoding.munrolibrarychallenge.data.errors.ErrorType
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
        val filterModel = FilterModel()
        filterModel.hillCategory = HillCategoryType.from(hillCategoryType)
        filterModel.sortHeightMType = SortType.from(sortHeightMType)
        filterModel.sortAlphabetType = SortType.from(sortAlphabetType)
        filterModel.sortLimit = sortLimit.toInt()
        val recordedMaxHeight = if (maxHeight.isBlank()) DEFAULT_DOUBLE else maxHeight.toDouble()
        val recordedMinHeight = if (minHeight.isBlank()) DEFAULT_DOUBLE else minHeight.toDouble()
        var errorType = if (recordedMaxHeight < recordedMinHeight) {
            ErrorType.MaxSmallerThanMin
        } else {
            filterModel.maxHeight = recordedMaxHeight
            filterModel.minHeight = recordedMinHeight
            ErrorType.NONE
        }

        if (errorType == ErrorType.NONE) {
            _filterModel.postValue(filterModel)
            _filterChanged.postValue(true)
        } else {
            _filterErrorState.postValue(MunroFilterViewState(showError = true, maxError = errorType))
        }
    }

    fun obtainFilterModel() = _filterModel.value
}