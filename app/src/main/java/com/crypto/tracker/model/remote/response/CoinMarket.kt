package com.crypto.tracker.model.remote.response

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class CoinMarket (
    @SerializedName("id")
    val id: String,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("current_price")
    val currentPrice: Double?,
    @SerializedName("min_price")
    var minPrice: Double?,
    @SerializedName("max_price")
    var maxPrice: Double?,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage: Double?,
    @SerializedName("last_updated")
    val lastUpdated: String?
)
