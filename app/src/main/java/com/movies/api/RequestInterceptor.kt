package com.movies.api

import com.movies.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val url = originalUrl.newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.MOVIEDB_API_KEY)
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        const val API_KEY: String = "api_key"
    }
}