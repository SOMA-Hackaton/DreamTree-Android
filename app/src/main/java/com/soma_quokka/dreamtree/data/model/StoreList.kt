package com.soma_quokka.dreamtree.data.model

import android.os.Parcelable
import com.soma_quokka.dreamtree.data.response.StoreResponseItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreList(val storeList: ArrayList<StoreResponseItem>) : Parcelable
