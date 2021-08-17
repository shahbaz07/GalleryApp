package com.changers.gallery.util

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("x-api-key", "96679290-7208-4449-b970-8cbc21425e98").build()
        return chain.proceed(request)
    }
}