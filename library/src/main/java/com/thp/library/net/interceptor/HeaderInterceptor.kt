package com.thp.library.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author thp
 * time 2020/4/15
 * desc:全局添加请求头部参数
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder?.apply {
            addHeader("Content-type", "application/json; charset=utf-8")
            addHeader(
                "User-Agent",
                "Mozilla/5.0 (compatible; mobile; android; zzb;) app-version/1.0 client-type/android-phone"
            )
        }
        return chain.proceed(builder.build());
    }
}