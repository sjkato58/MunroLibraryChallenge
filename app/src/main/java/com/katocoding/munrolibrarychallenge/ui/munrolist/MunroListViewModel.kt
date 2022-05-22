package com.katocoding.munrolibrarychallenge.ui.munrolist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.katocoding.munrolibrarychallenge.base.BaseViewModel
import com.katocoding.munrolibrarychallenge.data.ApiResponse
import com.katocoding.munrolibrarychallenge.data.errors.ErrorType
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

    var isInitialLoad = true

    fun getMunroList(loadStatus: MunroListLoadStatus) {
        if (canLoadData(loadStatus)) {
            _munroList.postValue(listOf(MunroListViewState(showLoading = true)))
            viewModelScope.launch {
                when (val apiResponse = munroListRepository.getMunroRecords()) {
                    is ApiResponse.Success -> {
                        apiResponse.data?.let { responseList ->
                            filterMunroListData(responseList)
                        }
                    }
                    is ApiResponse.Error -> publishMunroListErrorViewState(apiResponse)
                }
                isInitialLoad = false
            }
        }
    }

    fun canLoadData(
        loadStatus: MunroListLoadStatus
    ) = (loadStatus == MunroListLoadStatus.Reload) || (loadStatus == MunroListLoadStatus.Initial && isInitialLoad)

    fun filterMunroListData(recordList: List<MunroModel>) {
        when (val apiResponse = munroListFilter.checkFilterData(filterModel, recordList)) {
            is ApiResponse.Success -> {
                apiResponse.data?.let { responseList ->
                    publishMunroListViewState(responseList)
                }
            }
            is ApiResponse.Error -> publishMunroListErrorViewState(apiResponse)
        }
    }

    fun publishMunroListViewState(recordList: List<MunroModel>) {
        _munroList.value = recordList.map {
            MunroListViewState(
                name = it.name,
                heightM = it.heightM,
                hillCategory = it.hillCategory,
                gridReference = it.gridReference
            )
        }
    }

    fun publishMunroListErrorViewState(apiResponse: ApiResponse.Error<List<MunroModel>>) {
        _munroList.value = listOf(MunroListViewState(showError = true, errorType = apiResponse.errorType ?: ErrorType.NONE))
    }

    fun navigateToMunroFilter() {
        val converted = filterModel.convertToString()
        val destination = MunroListFragmentDirections.actionMunroListFragmentToMunroFilterFragment(converted)
        navigateInDirection(destination)
    }

    fun updateFilterModel(updatedFilterModel: String) {
        filterModel = FilterModel(updatedFilterModel)
        getMunroList(MunroListLoadStatus.Reload)
    }

}