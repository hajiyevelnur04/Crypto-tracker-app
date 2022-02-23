package com.crypto.tracker.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.crypto.tracker.model.remote.response.CryptoModel
import com.crypto.tracker.network.api.ProjectApi
import java.io.IOException

class CoinTickerListPagingSource(private val projectApi: ProjectApi) :
    PagingSource<Int, CryptoModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CryptoModel> {
        val nextPage = params.key ?: 1
        return try {
            val response = projectApi.getCoinTickers(nextPage,0)
            val nextKey = if (response.meta?.lastPage == nextPage) {
                null
            } else {
                nextPage + 1
            }
            LoadResult.Page(
                data = response.data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CryptoModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
