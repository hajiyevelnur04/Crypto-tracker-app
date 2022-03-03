package com.crypto.tracker.db

import androidx.room.*
import com.crypto.tracker.model.local.AlertType

@Database(
    entities =
    [AlertType::class],
    version = 1, exportSchema = false
)

abstract class CryptoTrackerDatabase : RoomDatabase() {
    abstract fun getDao(): CryptoTrackerDao
}