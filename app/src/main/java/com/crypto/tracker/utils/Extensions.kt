package com.crypto.tracker.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crypto.tracker.R
import com.crypto.tracker.network.handler.enums.Status
import com.crypto.tracker.network.handler.enums.ViewState
import com.google.android.material.button.MaterialButton
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

@BindingAdapter(value = ["setMinValue"])
fun MaterialTextView.bindTextMin(value: Double) {
    val valueTwoDigit = String.format("%.2f", value)
    this.text = "min -$valueTwoDigit"
    this.setTextColor(ContextCompat.getColor(this.context, R.color.red))
}

@BindingAdapter(value = ["setMaxValue"])
fun MaterialTextView.bindTextMax(value: Double) {
    val valueTwoDigit = String.format("%.2f", value)
    this.text = "max +$valueTwoDigit"
    this.setTextColor(ContextCompat.getColor(this.context, R.color.green))
}

@BindingAdapter(value = ["status", "brandError", "emptyData", "tags"], requireAll = false)
fun MultiStateView.changeStatus(status: Status?, brandError: String?, emptyData: String?, tags: List<String>?) {

    if (status == null) {
        this.viewState = ViewState.CONTENT
        return
    }


    val multiState: ViewState = when (status) {

        Status.SUCCESS -> ViewState.CONTENT
        Status.LOADING -> ViewState.LOADING
        Status.NETWORK -> ViewState.NETWORK
        Status.ERROR -> ViewState.CONTENT
        Status.EMPTY -> ViewState.EMPTY
        Status.TAG -> ViewState.TAG
    }

    this.viewState = multiState

    if (multiState == ViewState.EMPTY && !emptyData.isNullOrEmpty()) {

        this.getView(multiState)?.findViewById<TextView>(R.id.titleTxt)?.text = emptyData
    }

    when (status) {
        Status.NETWORK, Status.ERROR -> {
            // show some snackbar brat

        }
        else -> return
    }
}

@BindingAdapter("networkClickListener")
fun MultiStateView.fillNetwork(clickListener: View.OnClickListener?) {

    val view = this.rootView
    val button = view?.findViewById<MaterialButton>(R.id.tryBtn)
    button?.setOnClickListener(clickListener)

}