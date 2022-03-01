package com.crypto.tracker.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alertType")
data class AlertType(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "ids")
    val ids: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "symbol")
    val symbol: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "current_price")
    val currentPrice: Double,
    @ColumnInfo(name = "min_price")
    val minPrice: Double,
    @ColumnInfo(name = "max_price")
    val maxPrice: Double,
    @ColumnInfo(name = "last_updated")
    val last_updated: String,
    var isActive: Boolean? = false
)