package com.soma_quokka.dreamtree.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.soma_quokka.dreamtree.data.response.StoreResponse
import com.soma_quokka.dreamtree.network.RemoteDataSourceImpl
import com.soma_quokka.dreamtree.presentation.base.BaseViewModel
import com.soma_quokka.dreamtree.util.applySchedulers

class MapViewModel : BaseViewModel() {
    private val remoteDataSourceImpl = RemoteDataSourceImpl()
    private val _surroundStoreListLiveData = MutableLiveData<StoreResponse>()
    private val _searchResultStoreListLiveData = MutableLiveData<StoreResponse>()

    // 주변 가맹점 목록 LiveData
    val surroundStoreListLiveData: LiveData<StoreResponse>
        get() = _surroundStoreListLiveData

    // 검색결과 가맹점 목록 LiveData
    val searchResultStoreListLiveData: LiveData<StoreResponse>
        get() = _searchResultStoreListLiveData

    fun getSurroundStoreList(){
        addDisposable(
            remoteDataSourceImpl.getSurroundStoreList()
                .applySchedulers()
                .subscribe(
                    {
                        _surroundStoreListLiveData.value = it
                        Log.d("test1", it.toString())
                    },{
                        Log.d("test2", it.toString())
                    }
                )
        )
    }

    fun getSearchResultStoreList(){
        addDisposable(
            remoteDataSourceImpl.getSurroundStoreList()
                .applySchedulers()
                .subscribe(
                    {
                        _searchResultStoreListLiveData.value = it
                        Log.d("test1", it.toString())
                    },{
                        Log.d("test2", it.toString())
                    }
                )
        )
    }

    
}