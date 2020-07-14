package com.tw.wan.api

import com.thp.library.net.BaseNetWorkClient
import com.tw.wan.api.interceptor.AddCookiesInterceptor
import com.tw.wan.api.interceptor.SaveCookiesInterceptor
import okhttp3.OkHttpClient

/**
 * @author thp
 * time 2020/4/24
 * desc:网络请求
 */
class RetrofitClient private constructor() : BaseNetWorkClient() {

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = RetrofitClient()
    }

    //可以扩展增加
    override fun handleBuilder(builder: OkHttpClient.Builder) {
        builder.addInterceptor(SaveCookiesInterceptor())
        builder.addInterceptor(AddCookiesInterceptor())
    }
}