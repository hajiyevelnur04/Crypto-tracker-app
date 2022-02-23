package com.crypto.tracker.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.crypto.tracker.databinding.ItemCryptoBinding
import com.crypto.tracker.model.remote.response.CryptoModel

class HomeAdapter(private val listener: (CryptoModel) -> Unit) :
    PagingDataAdapter<CryptoModel, HomeAdapter.HomeAdapterViewHolder>(Companion) {

    class HomeAdapterViewHolder(val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<CryptoModel>() {
        override fun areItemsTheSame(oldItem: CryptoModel, newItem: CryptoModel): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: CryptoModel, newItem: CryptoModel): Boolean = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCryptoBinding.inflate(layoutInflater, parent, false)

        return HomeAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapterViewHolder, position: Int) {
        val model = getItem(position)
        holder.binding.model = model
        holder.itemView.setOnClickListener {
            listener.invoke(model!!)
        }
        holder.binding.executePendingBindings()
    }
}