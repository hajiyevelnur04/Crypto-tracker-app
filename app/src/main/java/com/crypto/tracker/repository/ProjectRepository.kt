package com.crypto.tracker.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.crypto.tracker.db.CryptoTrackerDao
import com.crypto.tracker.model.local.AlertType
import com.crypto.tracker.model.remote.Currency
import com.crypto.tracker.model.remote.response.CryptoModel
import com.crypto.tracker.network.api.ProjectApi
import com.crypto.tracker.network.handler.Resource
import com.crypto.tracker.network.handler.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ProjectRepository(
    private val api: ProjectApi,
    private val dao: CryptoTrackerDao,
    private val responseHandler: ResponseHandler
) {

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                responseHandler.handleSuccess(apiCall.invoke())
            } catch (e: Exception) {
                e.printStackTrace()
                responseHandler.handleException(e)
            }
        }
    }

    // Crypto List pagination is 100
    fun getCoinTicker(): Flow<PagingData<CryptoModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 100,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { CoinTickerListPagingSource(api) }
        ).flow
    }

    fun insertAlertType(model: AlertType){
        return dao!!.insert(alertType = model)!!
    }

    fun updateAlertType(model: AlertType){
        return dao!!.update(alertType = model)!!
    }

    fun getAllAlertType(): LiveData<List<AlertType?>> {
        return dao!!.getAlerts()
    }


    suspend fun getCoinMarket(currency: Currency) = safeApiCall { api.getCoinMarkets(currency.toString()) }

}
