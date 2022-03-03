package com.crypto.tracker.utils

import androidx.work.*
import com.crypto.tracker.utils.service.AlertService
import com.crypto.tracker.appContext
import com.crypto.tracker.model.local.AlertType
import com.crypto.tracker.model.remote.response.CoinMarket
import java.util.concurrent.TimeUnit

fun getAlertTypeConverted(coinMarket: CoinMarket, isActive: Boolean, minPrice: Double, maxPrice: Double): AlertType {
    return AlertType(
        ids = coinMarket.id,
        name = coinMarket.name!!,
        symbol = coinMarket.symbol!!,
        image = coinMarket.image!!,
        currentPrice = coinMarket.currentPrice!!,
        minPrice = minPrice,
        maxPrice = maxPrice,
        last_updated = coinMarket.lastUpdated!!,
        isActive = isActive
    )
}

fun startAlertsService() {
    WorkManager.getInstance(appContext!!).enqueueUniquePeriodicWork(
        SERVICE_ALERTS_ID, ExistingPeriodicWorkPolicy.KEEP,
        PeriodicWorkRequest.Builder(
            (AlertService::class.java as Class<out ListenableWorker?>), 15, TimeUnit.MINUTES
        ).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        )
            .build()
    )
}

fun cancelAlertsService() {
    WorkManager.getInstance(appContext!!).cancelUniqueWork(SERVICE_ALERTS_ID)
}