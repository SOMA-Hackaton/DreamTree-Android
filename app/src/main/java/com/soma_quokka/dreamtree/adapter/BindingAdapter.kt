package com.soma_quokka.dreamtree.adapter

import android.graphics.Movie
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("bind:item")
fun bindItem(recyclerView: RecyclerView, storeList: Any) {
    val adapter: StoreListAdapter? = recyclerView.adapter as StoreListAdapter?
    adapter?.setItem(storeList)
}