package com.soma_quokka.dreamtree.network

import com.soma_quokka.dreamtree.network.NetworkHelper.retrofitService

class RemoteDataSourceImpl : RetrofitService {
    override fun getSurroundStoreList() = retrofitService.getSurroundStoreList()
    override fun getSearchResultStoreList(userQuery: String) = retrofitService.getSearchResultStoreList(userQuery = userQuery)
}