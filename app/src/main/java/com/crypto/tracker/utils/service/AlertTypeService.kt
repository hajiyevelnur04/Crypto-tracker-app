package com.crypto.tracker.utils.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crypto.tracker.MainActivity
import com.crypto.tracker.R
import com.crypto.tracker.db.CryptoTrackerDao
import com.crypto.tracker.model.local.AlertType
import com.crypto.tracker.model.remote.response.SimplePriceResponse
import com.crypto.tracker.repository.ProjectRepository
import com.crypto.tracker.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class AlertTypeService(
    var dao:CryptoTrackerDao,
    var repository: ProjectRepository
) : LifecycleService() {

    val TAG = "AlertTypeService"
    private val binder = ServiceBinder()

    inner class ServiceBinder : Binder() {
        val service: AlertTypeService get() = this@AlertTypeService
    }
    companion object{
        var isRunning = false
    }

    private val _navigateToPriceList = MutableLiveData<SimplePriceResponse>()
    val navigateToPriceList: LiveData<SimplePriceResponse>
        get() = _navigateToPriceList

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when(it.action){
                ACTION_START_OR_RESUME_SERVICE ->{
                    Timber.d("Started or resumed service")
                }
                ACTION_PAUSE_SERVICE ->{
                    Timber.d("Paused service")
                }
                ACTION_STOP_SERVICE ->{
                    Timber.d("Stopped service")
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
        Log.d(TAG, "Service started")
        startForeground()
        isRunning = true
        setupMinMaxPriceChecker()

        navigateToPriceList.observe(this){

        }
        return START_STICKY
    }

    private fun setupMinMaxPriceChecker() {
        val activeAlertType = arrayListOf<AlertType>()
        CoroutineScope(Dispatchers.Main).launch {
            getAlertList().observe(this@AlertTypeService){
                it.forEach {
                    if(it!!.isActive == true){
                        activeAlertType.add(it)
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            activeAlertType.forEach {
                val data = repository.getSimplePrice(it.ids,it.symbol)
                _navigateToPriceList.postValue(data.data!!)
            }
        }

    }

    private fun getAlertList():LiveData<List<AlertType?>>{
        return dao.getAlerts()
    }

    private fun startForeground() {

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("CryptoTracker", "Price Checker Service",notificationManager)
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setOngoing(true)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("test")
            .setSmallIcon(R.drawable.ic_home)
            .setContentIntent(getMainActivityPendingIntent())

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java).also {
            it.action = ACTION_SHOW_TRACKING_FRAGMENT
        },
        FLAG_UPDATE_CURRENT
    )


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String, notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        stopForeground(true)
    }


}