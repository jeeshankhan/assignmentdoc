package com.video.docquityandroidassignment.api

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class HomeRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getDashDetails() = apiService.getDashDetails()
}