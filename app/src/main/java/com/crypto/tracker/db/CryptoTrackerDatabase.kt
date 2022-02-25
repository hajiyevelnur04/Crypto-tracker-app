package com.crypto.tracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.crypto.tracker.model.local.AlertType

@Database(
    entities =
    [
        AlertType::class,

    ], version = 1, exportSchema = false
)
abstract class CryptoTrackerDatabase : RoomDatabase() {

    abstract val dao: CryptoTrackerDao

    companion object {
        @Volatile
        private var instance: CryptoTrackerDatabase? = null
        fun getInstance(context: Context): CryptoTrackerDatabase? {
            if (instance != null) return instance!!

            synchronized(this) {

                instance = Room
                    .databaseBuilder(context, CryptoTrackerDatabase::class.java, "cryptoTracker.db")
                    .fallbackToDestructiveMigration()
                    .build().also {
                        instance = it
                    }

                return instance!!
            }

            fun destroyInstance() {
                instance = null
            }
        }
    }

}