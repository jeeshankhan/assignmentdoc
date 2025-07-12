package com.personalLoan.loan112.api

import com.video.docquityandroidassignment.api.ApiService


object ApiController {
    fun getApi() : ApiService = ApiService.create()
}