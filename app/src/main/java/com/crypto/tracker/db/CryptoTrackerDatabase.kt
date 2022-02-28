package com.crypto.tracker.db

import androidx.room.*
import com.crypto.tracker.model.local.AlertType

@Database(
    entities =
    [AlertType::class],
    version = 1, exportSchema = false
)

@TypeConverters()
abstract class CryptoTrackerDatabase : RoomDatabase() {
    abstract val dao: CryptoTrackerDao
}