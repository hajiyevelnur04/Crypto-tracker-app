package com.crypto.tracker.network.handler

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.RateLimiter

// 429 code handling
class RateLimitInterceptor : Interceptor {
    private val limiter: RateLimiter = RateLimiter.create(3.0)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        limiter.acquire(1)
        return chain.proceed(chain.request())
    }
}