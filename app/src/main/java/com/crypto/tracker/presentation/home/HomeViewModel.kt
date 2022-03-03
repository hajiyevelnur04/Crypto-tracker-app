package com.crypto.tracker.presentation.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crypto.tracker.base.BaseViewModel
import com.crypto.tracker.model.local.AlertType
import com.crypto.tracker.model.remote.ClickModel
import com.crypto.tracker.model.remote.Currency
import com.crypto.tracker.network.handler.enums.Status
import com.crypto.tracker.repository.ProjectRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel(private val repository: ProjectRepository): BaseViewModel(), LifecycleObserver {
    val adapter = HomeAdapter{
        _navigateToItemDetail.postValue(ClickModel(it,true))
        //to do on click
    }

    var currency: Currency = Currency.USD
    private val _navigateToItemDetail = MutableLiveData<ClickModel>()
    val navigateToItemDetail: LiveData<ClickModel>
        get() = _navigateToItemDetail

    init {
        getCoinMarket()
    }

    private fun getCoinMarket() {
        //changeStatus(Status.LOADING)
        coroutineScopeMain.launch {
            val serverResponse = repository.getCoinMarket(currency = currency)
            serverResponse.data.let(adapter::submitList)
            //changeStatus(Status.SUCCESS)
            //changeBrandError(serverResponse.message)
        }
    }

    fun addAlertType(alertType: AlertType){
        coroutineScopeIO.launch {
            repository.insertAlertType(alertType)
        }
    }

}