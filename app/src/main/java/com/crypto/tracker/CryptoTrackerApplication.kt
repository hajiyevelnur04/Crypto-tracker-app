package com.crypto.tracker

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.*
import com.crypto.tracker.di.injectFeature
import com.crypto.tracker.utils.LAST_SERVICES_CHECK
import com.crypto.tracker.utils.PreferenceHelper
import com.crypto.tracker.utils.SERVICE_ALERTS_ID
import com.crypto.tracker.utils.service.AlertService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
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

class CryptoTrackerApplication : Application() {

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
            injectFeature()
        }

        startServices()

        startFirebase()
    }

    private fun startFirebase() {
        FirebaseApp.initializeApp(this)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            val token = task.result
            Log.e("Data", token.toString())
        })

        FirebaseInstallations.getInstance().id
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
            })
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

    // work manager guarantees that service will executed and allows obervable
    private fun startServices() {


        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP
        // This is PeriodicWorkRequest it repeats every 15 minutes.
        val periodic = PeriodicWorkRequest.Builder(AlertService::class.java,15,TimeUnit.MINUTES)

        val workManager = WorkManager.getInstance(this)

        workManager.enqueueUniquePeriodicWork(
            SERVICE_ALERTS_ID,
            existingPeriodicWorkPolicy,
            periodic.setConstraints(constraints).build()!!
        )
        LAST_SERVICES_CHECK = System.currentTimeMillis()
    }

    fun onActivityResumed(activity: Activity?) {
        if (System.currentTimeMillis() - LAST_SERVICES_CHECK > 3600000) {
            startServices()
        }
    }

}