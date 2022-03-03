package com.crypto.tracker

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.work.*
import com.crypto.tracker.di.injectFeature
import com.crypto.tracker.utils.LAST_SERVICES_CHECK
import com.crypto.tracker.utils.PreferenceHelper
import com.crypto.tracker.utils.SERVICE_ALERTS_ID
import com.crypto.tracker.utils.service.AlertService
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import java.util.concurrent.TimeUnit


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

        FirebaseApp.initializeApp(this)
        // start koin injection
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CryptoTrackerApplication)
            injectFeature()
        }

        //startServices()
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

    // work manager guarantees that service will executed and allows obervable
    private fun startServices() {
        val build = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            SERVICE_ALERTS_ID, existingPeriodicWorkPolicy,
            PeriodicWorkRequest.Builder(
                (AlertService::class.java as Class<out ListenableWorker?>)!!, 15, TimeUnit.MINUTES
            ).setConstraints(build).build()!!
        )
        LAST_SERVICES_CHECK = System.currentTimeMillis()
    }

    fun onActivityResumed(activity: Activity?) {
        if (System.currentTimeMillis() - LAST_SERVICES_CHECK > 3600000) {
            startServices()
        }
    }

}