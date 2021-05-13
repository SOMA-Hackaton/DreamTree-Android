package com.soma_quokka.dreamtree.network

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface RetrofitService {
    @GET("/api/vi")
    fun getSurroundPlace()
}