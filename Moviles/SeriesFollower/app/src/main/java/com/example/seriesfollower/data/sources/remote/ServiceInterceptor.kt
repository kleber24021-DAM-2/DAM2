package com.example.seriesfollower.data.sources.remote

import okhttp3.Interceptor
import okhttp3.Response

class ServiceInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
            .addQueryParameter(ApiConsts.API_KEY, ApiConsts.API_KEY_VALUE)
            .addQueryParameter(ApiConsts.API_LANG, ApiConsts.API_LANG_VALUE)
            .build()
        val request = chain.request().newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}