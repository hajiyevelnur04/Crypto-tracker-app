package com.crypto.tracker

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.crypto.tracker.di.appModule
import com.crypto.tracker.utils.PreferenceHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


val appContext: Context? by lazy {
    CryptoTrackerApplication.appContext
}
val prefs: PreferenceHelper? by lazy {
    CryptoTrackerApplication.prefs
}



class CryptoTrackerApplication: Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var appContext: Context? = null
        var prefs: PreferenceHelper? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        prefs = PreferenceHelper()
        // start koin injection
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CryptoTrackerApplication)
            androidFileProperties()
            modules(listOf(appModule))
        }
    }
}