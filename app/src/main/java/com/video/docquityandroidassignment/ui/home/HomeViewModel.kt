package com.video.docquityandroidassignment.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.video.docquityandroidassignment.api.ApiService
import com.video.docquityandroidassignment.api.HomeRepository
import com.video.docquityandroidassignment.model.DashboardResponse
import com.video.docquityandroidassignment.ui.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _getDashResponse = MutableLiveData<ApiState<DashboardResponse>>()
    val getDashLiveData: LiveData<ApiState<DashboardResponse>> = _getDashResponse

    fun getDashboardDetails() {
        _getDashResponse.value = ApiState.loading()
        viewModelScope.launch {
            try {
                val res = repository.getDashDetails()
                if (res.status) {
                    _getDashResponse.postValue(ApiState.success(res))
                } else {
                    _getDashResponse.postValue(ApiState.error(res.message ?: "Something went wrong"))
                }
            } catch (e: Exception) {
                _getDashResponse.postValue(ApiState.error(e.localizedMessage ?: "Something went wrong"))
            }
        }
    }
}
