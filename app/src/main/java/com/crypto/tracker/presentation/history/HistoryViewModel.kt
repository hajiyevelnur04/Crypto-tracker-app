package com.crypto.tracker.presentation.history

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crypto.tracker.base.BaseViewModel
import com.crypto.tracker.model.local.AlertType
import com.crypto.tracker.repository.ProjectRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: ProjectRepository): BaseViewModel(), LifecycleObserver {

    val adapter = HistoryAdapter{
        // to do when switch changed
        val alertType = it
        alertType.isActive = alertType.isActive != true
        _navigateToItemStatus.postValue(alertType)
    }

    private val _navigateToItemStatus = MutableLiveData<AlertType>()
    val navigateToItemStatus: LiveData<AlertType>
        get() = _navigateToItemStatus


    fun update(alertType: AlertType){
        coroutineScopeIO.launch {
            repository.updateAlertType(alertType)
        }
    }

    fun getAllAlerts(): LiveData<List<AlertType?>>{
        return repository.getAllAlertType()
    }
}