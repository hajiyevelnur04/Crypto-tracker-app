package com.crypto.tracker.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.crypto.tracker.databinding.ItemCoinMarketBinding
import com.crypto.tracker.model.remote.response.CoinMarket

class HomeAdapter(private val listener: (CoinMarket) -> Unit) :
    ListAdapter<CoinMarket, HomeAdapter.HomeAdapterViewHolder>(Companion) {

    class HomeAdapterViewHolder(val binding: ItemCoinMarketBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<CoinMarket>() {
        override fun areItemsTheSame(oldItem: CoinMarket, newItem: CoinMarket): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: CoinMarket, newItem: CoinMarket): Boolean = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinMarketBinding.inflate(layoutInflater, parent, false)

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