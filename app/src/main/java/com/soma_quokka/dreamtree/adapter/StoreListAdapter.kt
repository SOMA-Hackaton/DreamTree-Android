package com.soma_quokka.dreamtree.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soma_quokka.dreamtree.BR
import com.soma_quokka.dreamtree.data.model.Store
import com.soma_quokka.dreamtree.databinding.StoreListItemBinding
/*
class StoreListAdapter(
) : RecyclerView.Adapter<StoreListAdapter.ViewHolder>() {
    private var items: List<Store> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StoreListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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
    ) : RecyclerView.ViewHolder (){

        fun bind(item: Store) {
            binding.setVariable(BR.store, store)
        }
    }

    fun setItems(items: List<Store>) {
        this.items = items
        notifyDataSetChanged()
    }
}*/