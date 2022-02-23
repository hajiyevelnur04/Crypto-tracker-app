package com.crypto.tracker.di

import com.crypto.tracker.HomeViewModel
import com.crypto.tracker.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel() }
    viewModel { HomeViewModel() }
}