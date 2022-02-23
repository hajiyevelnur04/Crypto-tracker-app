package com.crypto.tracker.model.remote.response

import com.google.gson.annotations.SerializedName

data class CryptoListResponse(
    @SerializedName("data")
    val data: List<CryptoModel>,
    @SerializedName("message")
    val message: String?,
    @SerializedName("links")
    val link: LinkModel?,
    @SerializedName("meta")
    val meta: MetaModel?
)
