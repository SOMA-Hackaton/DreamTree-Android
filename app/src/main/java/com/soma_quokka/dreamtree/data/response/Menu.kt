package com.soma_quokka.dreamtree.data.response

import android.annotation.SuppressLint
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Menu(
    val name: String,
    val price: String
) : Parcelable