package com.crypto.tracker.network

import com.crypto.tracker.BuildConfig
import com.crypto.tracker.network.api.ProjectApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    fun provideApi(): ProjectApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .hostnameVerifier(CryptoTrackerHostNameVerifier())
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(UnAuthorizedInterceptor())
            .retryOnConnectionFailure(true)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson().newBuilder().create()))
            .client(client)
            .build()
        return retrofit.create(ProjectApi::class.java)
    }

}