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
    private val _storeListLiveData = MutableLiveData<StoreResponse>()
    val storeListLiveData: LiveData<StoreResponse>
        get() = _storeListLiveData

    fun getStoreList(){
        addDisposable(
            remoteDataSourceImpl.getStoreList()
                .applySchedulers()
                .subscribe(
                    {
                        _storeListLiveData.value = it
                        Log.d("test1", it.toString())
                    },{
                        Log.d("test2", it.toString())
                    }
                )
        )
    }
}