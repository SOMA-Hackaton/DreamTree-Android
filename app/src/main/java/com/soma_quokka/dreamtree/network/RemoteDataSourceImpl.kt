package com.soma_quokka.dreamtree.network

import com.soma_quokka.dreamtree.network.NetworkHelper.retrofitService

class RemoteDataSourceImpl : RetrofitService {
    override fun getSurroundPlace() {
        TODO("Not yet implemented")
    }

    override fun getStoreList() = retrofitService.getStoreList()
}