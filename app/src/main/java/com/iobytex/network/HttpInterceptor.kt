package com.iobytex.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class HttpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        Timber.d(request.toString())

        return chain.proceed(request)
    }
}