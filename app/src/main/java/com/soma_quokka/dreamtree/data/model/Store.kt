package com.soma_quokka.dreamtree.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Store(
    val name: String,
    val type: String,
    val latitude: Double,
    val longtitude: Double
): Parcelable
