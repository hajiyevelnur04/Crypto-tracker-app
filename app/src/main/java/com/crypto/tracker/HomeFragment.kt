package com.crypto.tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.crypto.tracker.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater).apply {
            viewModel = homeViewModel
            lifecycleOwner = this@HomeFragment
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