package com.crypto.tracker.presentation.cointickers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crypto.tracker.databinding.FragmentCoinTickerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinTickerFragment : Fragment() {
    private val coinTickerViewModel: CoinTickerViewModel by viewModel()
    private val binding: FragmentCoinTickerBinding by lazy {
        FragmentCoinTickerBinding.inflate(layoutInflater).apply {
            viewModel = coinTickerViewModel
            lifecycleOwner = this@CoinTickerFragment
            executePendingBindings()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

}