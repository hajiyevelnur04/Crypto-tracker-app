package com.crypto.tracker.presentation.cointickers

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.crypto.tracker.R
import com.crypto.tracker.appContext
import com.crypto.tracker.base.BaseViewModel
import com.crypto.tracker.model.remote.ClickModel
import com.crypto.tracker.network.handler.enums.Status
import com.crypto.tracker.presentation.home.HomeAdapter
import com.crypto.tracker.repository.ProjectRepository
import com.crypto.tracker.utils.CryptoLoadStateAdapter
import kotlinx.coroutines.launch

class CoinTickerViewModel(private val repository: ProjectRepository): BaseViewModel() {

    val adapter = CoinTickerAdapter{
        //to do on click
    }

    private val _navigateToItemDetail = MutableLiveData<ClickModel>()
    val navigateToItemDetail: LiveData<ClickModel>
        get() = _navigateToItemDetail

    init {
        getCoinTickers()
    }

    private fun getCoinTickers(){
        coroutineScopeMain.launch {
            adapter.addLoadStateListener { loadState ->
                when (loadState.source.refresh) {
                    is LoadState.Loading -> {
                        changeStatus(Status.LOADING)
                    }
                    is LoadState.Error -> {
                        changeStatus(Status.NETWORK)
                    }
                    is LoadState.NotLoading -> {
                        if (loadState.append.endOfPaginationReached && adapter.itemCount < 1)
                            changeStatus(Status.EMPTY)
                        else
                            changeStatus(Status.SUCCESS)
                    }
                }
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let { changeBrandError(appContext?.resources?.getString(R.string.no_internet_title)) }

            }
            adapter.withLoadStateFooter(footer = CryptoLoadStateAdapter { adapter.retry() })
        }
        _navigateToItemDetail.value = ClickModel(null, false)
        onClickListener.postValue(View.OnClickListener { getCryptoes() })
    }

    fun getCryptoes() {
        coroutineScopeMain.launch {
            val data = repository.getCoinTicker().cachedIn(viewModelScope)

            data.collect {
                adapter.submitData(it)
            }

        }
    }
}