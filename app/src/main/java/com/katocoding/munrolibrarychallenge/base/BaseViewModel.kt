package com.katocoding.munrolibrarychallenge.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.katocoding.munrolibrarychallenge.util.SingleLiveEvent

abstract class BaseViewModel: ViewModel() {

    val navigationEvent: LiveData<NavController.() -> Any> get() = _navigationEvent
    private val _navigationEvent = SingleLiveEvent<NavController.() -> Any>()

    fun navigateInDirection(directions: NavDirections) {
        _navigationEvent.postValue {
            navigate(directions)
        }
    }
}