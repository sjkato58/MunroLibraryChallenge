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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MunroListViewModel @Inject constructor(
    private val munroListRepository: MunroListRepository
): BaseViewModel() {

    private val _munroList = MutableLiveData<List<MunroListViewState>>()
    val munroList: LiveData<List<MunroListViewState>> get() = _munroList

    var filterModel: FilterModel = FilterModel()

    fun getMunroList() {
        viewModelScope.launch {
            when (val apiResponse = munroListRepository.getMunroRecords(filterModel)) {
                is ApiResponse.Success -> {
                    apiResponse.data?.let { responseList ->
                        publishMunroListViewState(responseList)
                    }
                }
                is ApiResponse.Error -> publishMunroListErrorViewState(apiResponse)
            }
        }
    }

    fun publishMunroListViewState(responseList: List<MunroModel>) {
        Log.w("seiji", "recordSize: ${responseList.size}")
        /*responseList.forEachIndexed { index, mutableList ->
            Log.w("seiji", "record-$index: $mutableList")
        }*/
    }

    fun publishMunroListErrorViewState(apiResponse: ApiResponse.Error<List<MunroModel>>) {

    }


}