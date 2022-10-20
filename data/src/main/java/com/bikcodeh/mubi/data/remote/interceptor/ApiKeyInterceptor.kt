package com.bikcodeh.mubi.data.remote.interceptor

import com.bikcodeh.mubi.data.di.DB_API
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url = original.url.newBuilder().addQueryParameter("api_key", DB_API).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}