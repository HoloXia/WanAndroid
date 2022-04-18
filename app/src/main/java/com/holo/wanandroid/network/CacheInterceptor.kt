package com.holo.wanandroid.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
class CacheInterceptor(var day: Int = 7) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val maxStale = 60 * 60 * 24 * day
        response.newBuilder()
            .removeHeader("Pragma")
            .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
            .build()
        return response
    }
}