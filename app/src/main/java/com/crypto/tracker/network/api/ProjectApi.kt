package com.crypto.tracker.network.api

import com.crypto.tracker.model.remote.Currency
import com.crypto.tracker.model.remote.response.CoinListItem
import com.crypto.tracker.model.remote.response.CoinMarket
import com.crypto.tracker.model.remote.response.CryptoListResponse
import com.crypto.tracker.model.remote.response.SimplePriceResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectApi {
    // Cryptoes
    @GET("coin/{id}/tickers")
    suspend fun getCoinTickers(@Path("id") coinId: Int, id: Int): CryptoListResponse

    @GET("coins/markets")
    suspend fun getCoinMarkets(@Query("vs_currency") vsCurrency: String): List<CoinMarket>

    @GET("simple/price")
    suspend fun getSimplePrice(@Query("ids") ids: String,@Query("vs_currency") vsCurrency: String): SimplePriceResponse

    @GET("coins/list")
    suspend fun getCoinMarkets(): List<CoinListItem>

    @GET("simple/supported_vs_currencies")
    suspend fun getSupportedCurrencies(): List<String>
}
