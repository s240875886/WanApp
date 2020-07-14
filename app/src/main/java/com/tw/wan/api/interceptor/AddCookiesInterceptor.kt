package com.tw.wan.api.interceptor

import com.blankj.utilcode.util.SPUtils
import com.tw.wan.other.Constants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author thp
 * time 2020/6/2
 * desc:自动登陆 上传cookid
 */
class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val domain = request.url.host
        domain?.let {
            val cookie = SPUtils.getInstance().getString(Constants.COOKIE_KEY)
            if (!cookie.isNullOrEmpty()) {
                builder.addHeader(Constants.COOKIE_NAME, cookie)
                Constants.isLogin = true
            }
        }
        return chain.proceed(builder.build())
    }

}