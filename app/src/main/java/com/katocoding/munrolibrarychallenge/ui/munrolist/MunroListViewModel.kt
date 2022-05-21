package com.katocoding.munrolibrarychallenge.ui.munrolist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.katocoding.munrolibrarychallenge.base.BaseViewModel
import com.katocoding.munrolibrarychallenge.data.ApiResponse
import com.katocoding.munrolibrarychallenge.data.munrolist.MunroListRepository
import com.katocoding.munrolibrarychallenge.data.munrolist.MunroModel
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.FilterModel
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.MunroListFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MunroListViewModel @Inject constructor(
    private val munroListRepository: MunroListRepository,
    private val munroListFilter: MunroListFilter
): BaseViewModel() {

    private val _munroList = MutableLiveData<List<MunroListViewState>>()
    val munroList: LiveData<List<MunroListViewState>> get() = _munroList

    private var filterModel: FilterModel = FilterModel()

    fun getMunroList() {
        viewModelScope.launch {
            when (val apiResponse = munroListRepository.getMunroRecords()) {
                is ApiResponse.Success -> {
                    apiResponse.data?.let { responseList ->
                        filterMunroListData(responseList)
                    }
                }
                is ApiResponse.Error -> publishMunroListErrorViewState(apiResponse)
            }
        }
    }

    fun filterMunroListData(recordList: List<MunroModel>) {
        when (val apiResponse = munroListFilter.checkFilterData(filterModel, recordList)) {
            is ApiResponse.Success -> {
                apiResponse.data?.let { responseList ->
                    publishMunroListViewState(responseList)
                }
            }
            is ApiResponse.Error -> publishMunroListErrorViewState(apiResponse)
        }
        /*Log.w("seiji", "recordSize: ${filteredRecords.size}")
        filteredRecords.forEachIndexed { index, mutableList ->
            Log.w("seiji", "record-$index: $mutableList")
        }*/
    }

    fun publishMunroListViewState(recordList: List<MunroModel>) {
        Log.w("seiji", "recordSize: ${recordList.size}")
        /*recordList.forEachIndexed { index, mutableList ->
            Log.w("seiji", "record-$index: $mutableList")
        }*/
    }

    fun publishMunroListErrorViewState(apiResponse: ApiResponse.Error<List<MunroModel>>) {

    }

    fun updateFilterModel(updatedFilterModel: FilterModel) {
        filterModel = FilterModel(
            updatedFilterModel.hillCategory,
            updatedFilterModel.sortHeightMType,
            updatedFilterModel.sortAlphabetType,
            updatedFilterModel.sortLimit,
            updatedFilterModel.maxHeight,
            updatedFilterModel.minHeight
        )
    }

}