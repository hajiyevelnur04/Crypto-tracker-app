package com.crypto.tracker.base

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crypto.tracker.network.handler.enums.Status
import com.crypto.tracker.network.handler.enums.UiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseViewModel() : ViewModel() {

    var viewModelJob = Job()

    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable->
        throwable.printStackTrace()
    }

    val coroutineScopeMain = CoroutineScope(viewModelJob + Dispatchers.Main + exceptionHandler)

    val coroutineScopeIO = CoroutineScope(viewModelJob + Dispatchers.IO + exceptionHandler)

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState>
        get() = _uiState

    private val _brandError = MutableLiveData<String>()
    val brandError: LiveData<String>
        get() = _brandError

    val onClickListener = MutableLiveData<View.OnClickListener>()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun changeUiState(uiState: UiState) {
        _uiState.value = uiState
    }

    fun changeStatus(status: Status) {
        _status.value = status
    }

    fun changeBrandError(message: String?) {
        _brandError.value = message!!
    }

}