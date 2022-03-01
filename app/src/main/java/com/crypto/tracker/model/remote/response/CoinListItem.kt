package com.crypto.tracker.model.remote.response

import com.google.gson.annotations.SerializedName

data class CoinListItem (
    @SerializedName("id")
    val id: String,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("name")
    val name: String?
)
