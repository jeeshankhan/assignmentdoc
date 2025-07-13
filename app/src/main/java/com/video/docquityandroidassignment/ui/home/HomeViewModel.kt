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
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {


    private val _getDashResponse = MutableLiveData<ApiState<DashboardResponse>>()
    val getDashLiveData: LiveData<ApiState<DashboardResponse>> = _getDashResponse


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getMaritalStatus( api: ApiService) {
        _getDashResponse.value = ApiState.loading()
        viewModelScope.launch {
            try {
                val res = HomeRepository.getDhashDetails( api = api)
                if (res.status) {
                    Log.d("tag", "area: $res")
                    _getDashResponse.postValue(ApiState.success(res))
                } else {
                    _getDashResponse.postValue(
                        ApiState.error(
                            res.message ?: "Something went wrong"
                        )
                    )
                }
            } catch (e: Exception) {
                Log.d("tag", "exp: $e")
                _getDashResponse.postValue(
                    ApiState.error(
                        e.localizedMessage ?: "Something went wrong"
                    )
                )
            }
        }
    }
}