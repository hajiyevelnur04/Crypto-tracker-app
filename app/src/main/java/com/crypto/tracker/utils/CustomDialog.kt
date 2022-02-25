package com.crypto.tracker.utils

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.crypto.tracker.R
import com.crypto.tracker.databinding.FragmentAlertBinding
import com.crypto.tracker.model.remote.response.CoinMarket


class CustomDialog(
    private var coinMarket: CoinMarket,
    private val clickListener: ButtonClick
) : AppCompatDialogFragment() {

    val binding: FragmentAlertBinding by lazy {
        FragmentAlertBinding.inflate(layoutInflater).apply {
            model = coinMarket
            executePendingBindings()
        }
    }

    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(requireContext().getString(R.string.alert_title))
        builder.setView(binding.root)
        binding.continueBtn.setOnClickListener {
            clickListener.onClick(model = coinMarket)
            dismiss()
        }
        return builder.create()
    }

    class ButtonClick(val clickOkListener: (model: CoinMarket?) -> Unit) {
        fun onClick(model: CoinMarket?) = clickOkListener(model)
    }
}