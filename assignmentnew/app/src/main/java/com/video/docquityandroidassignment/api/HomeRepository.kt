package com.video.docquityandroidassignment.api


object HomeRepository {

    suspend fun getDhashDetails( api: ApiService) = api.getDashDetails()

}