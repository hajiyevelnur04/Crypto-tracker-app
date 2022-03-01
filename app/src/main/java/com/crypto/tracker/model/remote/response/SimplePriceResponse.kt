package com.crypto.tracker.model.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SimplePriceResponse(
    val coins: Map<String, String>
) : Parcelable
