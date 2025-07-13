package com.video.docquityandroidassignment.api


import com.google.gson.GsonBuilder
import com.personalLoan.loan112.ui.utils.ApiEndPoint
import com.video.docquityandroidassignment.model.DashboardResponse
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET(ApiEndPoint.DASHBOARD)
    suspend fun getDashDetails():DashboardResponse
    companion object {
        fun create(): ApiService {
            val logger =
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

            val authInterceptor = Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type","application/json")
                    .addHeader("Accept","application/json")
                    .addHeader("Auth","")
                    .build()
                chain.proceed(request)
            }
            val gson = GsonBuilder()
                .disableHtmlEscaping() // Prevents unwanted character escaping
                .create()

            val client = OkHttpClient.Builder().addInterceptor(logger)
                .addInterceptor(authInterceptor)
                .callTimeout(80, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(80, TimeUnit.SECONDS)
                .writeTimeout(80, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("")
                .client(client)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(ApiService::class.java)
        }
    }
}
