package com.crypto.tracker.utils

import androidx.work.*
import com.crypto.tracker.appContext
import com.crypto.tracker.model.local.AlertType
import com.crypto.tracker.model.remote.response.CoinMarket
import com.crypto.tracker.utils.service.AlertService
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

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP

    val periodic = PeriodicWorkRequestBuilder<AlertService>(15,TimeUnit.MINUTES).
    setConstraints(constraints).
    build()

    WorkManager.getInstance(appContext!!).enqueueUniquePeriodicWork(
        SERVICE_ALERTS_ID,
        existingPeriodicWorkPolicy,
        periodic!!
    )

}

fun cancelAlertsService() {
    WorkManager.getInstance(appContext!!).cancelUniqueWork(SERVICE_ALERTS_ID)
}

fun isTargetAbove(d: Double?, d2: Double?): Boolean {
    return !(d == null || d2 == null || d2.toDouble() == java.lang.Double.longBitsToDouble(1) || d.toDouble() < d2.toDouble())
}

fun isTargetBelow(d: Double?, d2: Double?): Boolean {
    return !(d == null || d2 == null || d2.toDouble() == java.lang.Double.longBitsToDouble(1) || d.toDouble() > d2.toDouble())
}