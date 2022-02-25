package com.crypto.tracker.presentation.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.crypto.tracker.databinding.ItemAlertTypeBinding
import com.crypto.tracker.model.local.AlertType


class HistoryAdapter(private val listener: (AlertType) -> Unit) :
    ListAdapter<AlertType, HistoryAdapter.HomeAdapterViewHolder>(Companion) {

    class HomeAdapterViewHolder(val binding: ItemAlertTypeBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<AlertType>() {
        override fun areItemsTheSame(oldItem: AlertType, newItem: AlertType): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: AlertType, newItem: AlertType): Boolean = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAlertTypeBinding.inflate(layoutInflater, parent, false)

        return HomeAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapterViewHolder, position: Int) {
        val model = getItem(position)
        holder.binding.model = model
        holder.itemView.setOnClickListener {
            listener.invoke(model!!)
        }
        holder.binding.isActive.setOnClickListener{
            listener.invoke(model!!)
        }
        holder.binding.executePendingBindings()
    }

}