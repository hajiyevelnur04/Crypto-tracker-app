package com.crypto.tracker.utils.service

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.crypto.tracker.model.local.AlertType
import com.crypto.tracker.repository.ProjectRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlertService(
    var repository: ProjectRepository,
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    var alertTypeList = arrayListOf<AlertType>()
    var numberOfRequest = -1
    override fun doWork(): Result {
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllAlertType()
            alertTypeList = data.value as ArrayList<AlertType>
        }
        numberOfRequest = alertTypeList.size
        return Result.success()
    }
}