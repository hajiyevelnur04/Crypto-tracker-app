package com.crypto.tracker.utils

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crypto.tracker.R
import com.google.android.material.textview.MaterialTextView

@BindingAdapter(value = ["setAdapter"])
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
    this.run {
        this.setHasFixedSize(false)
        this.overScrollMode = View.OVER_SCROLL_NEVER
        this.adapter = adapter
    }
}

@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url: String?) {
    Glide.with(this.context).load(if (url != null && url.isNotBlank()) url else com.google.android.material.R.drawable.mtrl_ic_error).into(this)
}

@BindingAdapter(value = ["setDoubleText"])
fun MaterialTextView.bindText(value: Double) {
    val valueTwoDigit = String.format("%.2f", value)
    if(value<0){
        this.text = "$valueTwoDigit"
        this.setTextColor(ContextCompat.getColor(this.context, R.color.red))
    } else {
        this.text = "+$valueTwoDigit"
        this.setTextColor(ContextCompat.getColor(this.context, R.color.green))
    }
}