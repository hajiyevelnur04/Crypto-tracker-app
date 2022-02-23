package com.crypto.tracker.model.remote.response


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CryptoModel(
    val id: String,
    val title: String
): Parcelable