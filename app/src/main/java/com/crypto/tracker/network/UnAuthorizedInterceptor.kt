package com.crypto.tracker.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class UnAuthorizedInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        when (response.code) {
            401 -> {
                // if you logout from other platform such as web please also close app or change logic
            }
        }
        return response
    }
}