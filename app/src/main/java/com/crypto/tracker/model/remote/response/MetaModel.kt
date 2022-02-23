package com.crypto.tracker.model.remote.response

import com.google.gson.annotations.SerializedName

data class MetaModel(
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("from")
    val from: Int?,
    @SerializedName("last_page")
    val lastPage: Int?,
    @SerializedName("path")
    val path: String?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("to")
    val to: Int?,
    @SerializedName("total")
    val total: Int?
)
