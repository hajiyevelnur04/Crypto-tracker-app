package com.crypto.tracker.utils.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import com.crypto.tracker.R
import com.crypto.tracker.model.local.AlertType
import com.crypto.tracker.model.remote.request.MyJsonObjectRequest
import com.crypto.tracker.model.remote.response.VolleyJsonObjectResponse
import com.crypto.tracker.repository.ProjectRepository
import com.crypto.tracker.utils.PRICE_LIST
import com.crypto.tracker.utils.PRICE_LIST_CURRENCY
import com.crypto.tracker.utils.isTargetAbove
import com.crypto.tracker.utils.isTargetBelow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class AlertService(
    var repository: ProjectRepository,
    val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams), VolleyJsonObjectResponse {
    var alertTypeList = arrayListOf<AlertType>()
    var numberOfRequest = -1

    private val notificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val notificationId: Int = 500
    private val notificationChannel: String = "demo"

    override fun doWork(): Result {
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllAlertType()
            alertTypeList = data.value as ArrayList<AlertType>
        }
        numberOfRequest = alertTypeList.size
        val newRequestQueue = Volley.newRequestQueue(context)
        alertTypeList.forEach{
            // get active alerts and send req queue
            if(it.isActive == true){
                newRequestQueue.add(MyJsonObjectRequest(0,PRICE_LIST+ it.id + PRICE_LIST_CURRENCY,
                    JSONObject(),"",it,this).getJsonObjectRequest())
            }
        }
        return Result.success()
    }

    override fun onError(volleyError: VolleyError?, str: String?) {

    }

    override fun onResponse(jSONObject: JSONObject?, str: String?, alertItem: AlertType?) {
        numberOfRequest--
        if (jSONObject != null && alertItem != null) {
            var valueFromServer = jSONObject.getJSONObject(alertItem.ids).getDouble(alertItem.ids)
            if(isTargetAbove(valueFromServer,alertItem.maxPrice)){
                // show notification for max
                displayNotification(alertItem)
            }

            if(isTargetBelow(valueFromServer,alertItem.minPrice)){
                // show notification for min
                displayNotification(alertItem)
            }
        }

    }

    @SuppressLint("RemoteViewLayout")
    private fun displayNotification(alertItem: AlertType?) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannel,
                notificationChannel,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(false)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder =
            NotificationCompat.Builder(applicationContext, notificationChannel)

        val remoteView = RemoteViews(applicationContext.packageName, R.layout.custom_notification)
        remoteView.setImageViewResource(R.id.image_ntf, R.drawable.ic_home)
        remoteView.setTextViewText(R.id.title_ntf, alertItem!!.name)
        remoteView.setTextViewText(R.id.text_ntf, "min:" + alertItem!!.minPrice.toString() + " max:" + alertItem!!.maxPrice.toString())
        notificationBuilder
            .setContent(remoteView)
            .setSmallIcon(R.drawable.ic_home)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

}