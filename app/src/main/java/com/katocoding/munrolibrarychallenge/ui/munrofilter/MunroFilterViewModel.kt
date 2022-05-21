package com.katocoding.munrolibrarychallenge.ui.munrofilter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.katocoding.munrolibrarychallenge.base.BaseViewModel
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.FilterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MunroFilterViewModel @Inject constructor(

): BaseViewModel() {

    private val _filterModel = MutableLiveData<FilterModel>()
    val filterModel: LiveData<FilterModel> get() = _filterModel

    fun updateFilterModel(newFilterModel: String) {
        _filterModel.postValue(FilterModel(newFilterModel))
    }


}