package com.soma_quokka.dreamtree.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soma_quokka.dreamtree.data.response.StoreResponse
import com.soma_quokka.dreamtree.data.response.StoreResponseItem
import com.soma_quokka.dreamtree.databinding.StoreListItemBinding

class StoreListAdapter(
    val itemClick: (StoreResponseItem) -> Unit
) : RecyclerView.Adapter<StoreListAdapter.ViewHolder>() {
    private var items: List<StoreResponseItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            StoreListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(
        private val binding: StoreListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StoreResponseItem) {
            binding.storeName.text = item.name
            binding.storeType.text = item.type
            binding.storeRating.rating = item.rating.toFloat()
            binding.storeAddress.text = item.address

            binding.root.setOnClickListener {
                itemClick(item)
            }
        }
    }

    fun setItem(items: StoreResponse) {
        this.items = items
        notifyDataSetChanged()
    }

    // TODO("해당 가맹점 간의 거리 계산")
    fun getDistance() {}
}