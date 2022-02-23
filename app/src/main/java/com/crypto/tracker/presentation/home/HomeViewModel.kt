package com.crypto.tracker.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crypto.tracker.base.BaseViewModel
import com.crypto.tracker.model.remote.ClickModel
import com.crypto.tracker.repository.ProjectRepository
import kotlinx.coroutines.launch


class HomeViewModel(private val repository: ProjectRepository): BaseViewModel() {
    val adapter = HomeAdapter{
        //to do on click
    }
    private val _navigateToItemDetail = MutableLiveData<ClickModel>()
    val navigateToItemDetail: LiveData<ClickModel>
        get() = _navigateToItemDetail

    init {
        getCoinMarket()
    }

    private fun getCoinMarket() {
        coroutineScopeMain.launch {
            //val data = repository.
        }

    }

}