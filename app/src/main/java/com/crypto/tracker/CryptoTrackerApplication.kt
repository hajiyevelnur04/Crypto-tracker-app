package com.crypto.tracker

import android.app.Application
import android.content.Context
import com.crypto.tracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


val appContext: Context? by lazy {
    CryptoTrackerApplication.appContext
}

class CryptoTrackerApplication: Application() {

    companion object {
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        // start koin injection
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CryptoTrackerApplication)
            androidFileProperties()
            modules(listOf(appModule))
        }
    }
}