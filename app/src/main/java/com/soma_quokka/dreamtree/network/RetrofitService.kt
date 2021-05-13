package com.soma_quokka.dreamtree.network

import com.soma_quokka.dreamtree.data.response.StoreResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RetrofitService {
    @GET("/")
    fun getSurroundStoreList(): Single<StoreResponse>

    @GET("/keyword/")
    fun getSearchResultStoreList(
        @Query(value = "storename", encoded = true) userQuery: String
    ): Single<StoreResponse>
}