package com.crypto.tracker.network.api

import com.crypto.tracker.model.remote.Currency
import com.crypto.tracker.model.remote.response.CoinMarket
import com.crypto.tracker.model.remote.response.CryptoListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectApi {
    // Cryptoes
    @GET("coin/{id}/tickers")
    suspend fun getCoinTickers(@Path("id") coinId: Int, id: Int): CryptoListResponse

    @GET("coin/markets")
    suspend fun getCoinMarkets(@Query("vs_currency") vsCurrency: String): List<CoinMarket>
}
