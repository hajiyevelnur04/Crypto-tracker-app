package com.crypto.tracker.di

import com.crypto.tracker.presentation.home.HomeViewModel
import com.crypto.tracker.MainViewModel
import com.crypto.tracker.appContext
import com.crypto.tracker.db.DatabaseClient.provideCryptoTrackerDao
import com.crypto.tracker.db.DatabaseClient.provideDatabase
import com.crypto.tracker.network.AuthInterceptor
import com.crypto.tracker.network.RetrofitClient
import com.crypto.tracker.network.handler.ResponseHandler
import com.crypto.tracker.presentation.cointickers.CoinTickerViewModel
import com.crypto.tracker.presentation.history.HistoryViewModel
import com.crypto.tracker.repository.ProjectRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { ResponseHandler() }
    factory { AuthInterceptor() }

    single { RetrofitClient.provideApi() }
    factory { ProjectRepository(get(), get(),get()) }

    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { CoinTickerViewModel(get()) }

    single { provideDatabase(appContext!!) }
    single { provideCryptoTrackerDao(get()) }

}