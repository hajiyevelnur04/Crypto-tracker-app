package com.crypto.tracker.presentation.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crypto.tracker.databinding.FragmentHistoryBinding
import com.crypto.tracker.utils.startAlertsService
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private val historyViewModel: HistoryViewModel by viewModel()
    private val binding: FragmentHistoryBinding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater).apply {
            viewModel = historyViewModel
            lifecycleOwner = this@HistoryFragment
            executePendingBindings()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        historyViewModel.getAllAlerts().observe(viewLifecycleOwner){
            it.let(historyViewModel.adapter::submitList)
            startAlertsService()
        }
        historyViewModel.navigateToItemStatus.observe(viewLifecycleOwner){
            historyViewModel.update(it)
        }
        return binding.root
    }
}