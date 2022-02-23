package com.crypto.tracker.model.remote.response

import com.google.gson.annotations.SerializedName

data class CoinMarket (
    @SerializedName("id")
    val id: Int,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("current_price")
    val currentPrice: Long?,
    @SerializedName("last_updated")
    val lastUpdated: String?

)
