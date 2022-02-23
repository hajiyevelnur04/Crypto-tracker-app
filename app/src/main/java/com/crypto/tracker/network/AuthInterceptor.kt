package com.crypto.tracker.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder().apply {
                addHeader("accept", "application/json")
            }
                .build()
        return chain.proceed(request)
    }
}
