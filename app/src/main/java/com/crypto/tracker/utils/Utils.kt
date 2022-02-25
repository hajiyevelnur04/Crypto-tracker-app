package com.crypto.tracker.utils

import com.crypto.tracker.model.local.AlertType
import com.crypto.tracker.model.remote.response.CoinMarket

fun getAlertTypeConverted(coinMarket: CoinMarket, isActive: Boolean, minPrice: Double, maxPrice: Double): AlertType {
    return AlertType(
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