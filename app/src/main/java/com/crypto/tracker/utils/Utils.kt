package com.crypto.tracker.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.crypto.tracker.R

fun showDialog(context: Context) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val dialogValueBinding: ViewDataBinding? =
        DataBindingUtil.inflate(
            LayoutInflater.from(dialog.context),
            R.layout.dialog_value,
            null,
            false
        )
    dialog.setTitle(context.getString(R.string.alert_title))
    dialog.setContentView(dialogValueBinding!!.root)
    dialog.setCanceledOnTouchOutside(false)
    dialog.show()
    /*dialogValueBinding.fromEdit.setOnClickListener {
        dialog.cancel()
    }
    dialogValueBinding.toEdit.setOnClickListener {
        dialog.cancel()
    }*/
}