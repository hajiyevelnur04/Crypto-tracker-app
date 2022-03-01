package com.crypto.tracker.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.crypto.tracker.databinding.FragmentHomeBinding
import com.crypto.tracker.model.remote.response.CoinMarket
import com.crypto.tracker.utils.CustomDialog
import com.crypto.tracker.utils.getAlertTypeConverted

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
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
        homeViewModel.navigateToItemDetail.observe(viewLifecycleOwner){
            if(it.clicked){
                val model = it.item as CoinMarket
                startDialog(model)
            }
        }
        return binding.root
    }

    private fun startDialog(coinMarket: CoinMarket){
        var customDialog = CustomDialog(coinMarket, CustomDialog.ButtonClick {
            // set default selected cryptocurrency alert functionality on or off
            homeViewModel.addAlertType(getAlertTypeConverted(coinMarket,true, it!!.minPrice!!, it!!.maxPrice!!))
        })
        customDialog.show(childFragmentManager,"tag")
    }

}