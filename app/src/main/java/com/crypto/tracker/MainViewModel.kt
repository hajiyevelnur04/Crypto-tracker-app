package com.crypto.tracker

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crypto.tracker.base.BaseViewModel
import com.crypto.tracker.model.local.AlertType
import com.crypto.tracker.model.remote.ClickModel
import com.crypto.tracker.model.remote.response.CoinMarket
import com.crypto.tracker.repository.ProjectRepository
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel(), LifecycleObserver {

}