package com.crypto.tracker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.crypto.tracker.utils.ACTION_PAUSE_SERVICE
import com.crypto.tracker.utils.ACTION_START_OR_RESUME_SERVICE
import com.crypto.tracker.utils.ACTION_STOP_SERVICE
import com.crypto.tracker.utils.CHANNEL_ID
import timber.log.Timber

class AlertTypeService : LifecycleService() {

    val TAG = "AlertTypeService"
    private val binder = ServiceBinder()

    inner class ServiceBinder : Binder() {
        val service: AlertTypeService get() = this@AlertTypeService
    }
    companion object{
        var isRunning = false
    }

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
        return START_STICKY
    }

    private fun setupMinMaxPriceChecker() {
        TODO("Not yet implemented")
    }

    private fun startForeground() {
        var channelId = CHANNEL_ID

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) channelId =
            createNotificationChannel("CryptoTracker", "Price Checker Service")

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("type", 1)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("test")
            .setSmallIcon(R.drawable.ic_home)
            .setContentIntent(pendingIntent)
            .build()


        startForeground(12345, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)

        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        channel.lightColor = Color.BLUE

        val service = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)

        return channelId
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