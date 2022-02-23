package com.crypto.tracker.di

import com.crypto.tracker.presentation.home.HomeViewModel
import com.crypto.tracker.MainViewModel
import com.crypto.tracker.presentation.cointickers.CoinTickerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { CoinTickerViewModel(get()) }

}