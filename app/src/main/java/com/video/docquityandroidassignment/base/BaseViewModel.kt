package com.video.docquityandroidassignment.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel:ViewModel() {

    private val _navigationStatus = MutableLiveData<String>()
    val navigationStatus:LiveData<String> get() = _navigationStatus

    private val _errorStatus = MutableLiveData<Int>()
    val errorStatus:LiveData<Int> get() = _errorStatus
    fun updateNavigationStatus(status:String){
        _navigationStatus.value = status
    }

    fun errorStatusUpdate(status:Int){
        _errorStatus.value = status
    }

}