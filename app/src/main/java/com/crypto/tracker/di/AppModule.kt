package com.crypto.tracker.di

import androidx.room.Room
import androidx.work.ExistingWorkPolicy
import androidx.work.PeriodicWorkRequest

import androidx.work.WorkManager
import com.crypto.tracker.presentation.home.HomeViewModel
import com.crypto.tracker.MainViewModel
import com.crypto.tracker.db.CryptoTrackerDatabase
import com.crypto.tracker.network.AuthInterceptor
import com.crypto.tracker.network.RetrofitClient
import com.crypto.tracker.network.handler.ResponseHandler
import com.crypto.tracker.presentation.cointickers.CoinTickerViewModel
import com.crypto.tracker.presentation.history.HistoryViewModel
import com.crypto.tracker.repository.ProjectRepository
import com.crypto.tracker.utils.DATABASE_NAME
import com.crypto.tracker.utils.SERVICE_ALERTS_ID
import com.crypto.tracker.utils.service.AlertService
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import java.util.concurrent.TimeUnit


fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(viewModelModule,
            netWorkModule,
            repositoryModule,
            databaseModule,
            serviceModule)
    )
}

val viewModelModule = module {
    viewModel { MainViewModel(repository = get()) }
    viewModel { HomeViewModel(repository = get()) }
    viewModel { HistoryViewModel(repository = get()) }
    viewModel { CoinTickerViewModel(repository = get()) }
}

val repositoryModule = module {
    factory { ProjectRepository(api = get(), dao = get(),responseHandler = get()) }
}

val netWorkModule = module {
    factory { ResponseHandler() }
    factory { AuthInterceptor() }
    single { RetrofitClient.provideApi() }
}

val databaseModule = module {
    single { Room.databaseBuilder(androidApplication(), CryptoTrackerDatabase::class.java, DATABASE_NAME).build() }
    single { get<CryptoTrackerDatabase>().getDao() }
}

val serviceModule = module {
    //single { AlertTypeService(get(),get()) }
    single { AlertService(repository = get(),context = androidApplication(),workerParams = get()) }
}