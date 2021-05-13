package com.soma_quokka.dreamtree.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkHelper {
    private const val serverBaseUrl = "http://domain.com/api"

    var token: String = ""

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        })
        .addInterceptor {
            // Request
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()

            Log.d("OkHTTP", "request: ${it.request()}")
            Log.d("OkHTTP", "request header: ${it.request().headers}")

            // Response
            val response = it.proceed(request)

            Log.d("OkHTTP", "response : $response")
            Log.d("OkHTTP", "response header: ${response.headers}")
            response
        }.build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(serverBaseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService: RetrofitService = retrofit.create(RetrofitService::class.java)
}
