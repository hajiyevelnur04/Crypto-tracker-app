package com.crypto.tracker.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.crypto.tracker.model.remote.response.CryptoModel
import com.crypto.tracker.network.api.ProjectApi
import com.crypto.tracker.network.handler.ResponseHandler
import kotlinx.coroutines.flow.Flow

class ProjectRepository(
    private val api: ProjectApi,
    private val responseHandler: ResponseHandler
) {

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



}
