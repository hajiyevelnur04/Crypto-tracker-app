package com.crypto.tracker.model.remote.response

import com.google.gson.annotations.SerializedName

data class LinkModel(
    @SerializedName("first")
    val first: String?,
    @SerializedName("last")
    val last: String?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("prev")
    val prev: String?
)
