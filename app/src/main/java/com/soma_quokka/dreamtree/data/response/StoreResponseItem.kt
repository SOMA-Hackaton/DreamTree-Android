package com.soma_quokka.dreamtree.data.response

import android.annotation.SuppressLint
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng

@SuppressLint("ParcelCreator")
@Parcelize
data class StoreResponseItem(
    val address: String,
    val dong: String,
    val imgurl: String,
    val latitude: Double,
    val longitude: Double,
    val menus: List<Menu>,
    val name: String,
    val phoneNumber: String,
    val rating: Double,
    val type: String
) : TedClusterItem, Parcelable {
    override fun getTedLatLng(): TedLatLng {
        return TedLatLng(latitude, longitude)
    }
}