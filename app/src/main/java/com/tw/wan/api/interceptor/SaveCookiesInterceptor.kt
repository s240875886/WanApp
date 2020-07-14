package com.tw.wan.api.interceptor

import com.blankj.utilcode.util.SPUtils
import com.tw.wan.other.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.lang.StringBuilder


/**
 * @author thp
 * time 2020/6/1
 * desc:保存cookie
 */
class SaveCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        val requestUrl = request.url.toString()
        if (requestUrl.contains(Constants.USER_LOGIN_KEY) || requestUrl.contains(Constants.USER_REGISTER_KEY)) {
            val cookies: List<String> = response.headers(Constants.COOKIE_KEY)
            saveCookie(getStringCookie(cookies))
        }
        return response
    }

    private fun saveCookie(cookie: String) {
        cookie?.let {
            SPUtils.getInstance().put(Constants.COOKIE_KEY, cookie)
        }

    }

    private fun getStringCookie(it: List<String>): String {
        if (it.isEmpty()) {
            return ""
        }

        val stringBuilder = StringBuilder()

        it.forEach { cookie ->
            stringBuilder.append(cookie).append(";")
        }

        if (stringBuilder.isEmpty()) {
            return ""
        }
        //末尾的";"去掉
        return stringBuilder.deleteCharAt(stringBuilder.length - 1).toString()
    }

}