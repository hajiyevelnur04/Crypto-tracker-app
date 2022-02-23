package com.crypto.tracker.presentation.cointickers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.crypto.tracker.databinding.ItemCoinTickerBinding
import com.crypto.tracker.model.remote.response.CryptoModel

class CoinTickerAdapter(private val listener: (CryptoModel) -> Unit) :
    PagingDataAdapter<CryptoModel, CoinTickerAdapter.CoinTickerAdapterViewHolder>(Companion) {

    class CoinTickerAdapterViewHolder(val binding: ItemCoinTickerBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<CryptoModel>() {
        override fun areItemsTheSame(oldItem: CryptoModel, newItem: CryptoModel): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: CryptoModel, newItem: CryptoModel): Boolean = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinTickerAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinTickerBinding.inflate(layoutInflater, parent, false)

        return CoinTickerAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinTickerAdapterViewHolder, position: Int) {
        val model = getItem(position)
        holder.binding.model = model
        holder.itemView.setOnClickListener {
            listener.invoke(model!!)
        }
        holder.binding.executePendingBindings()
    }
}