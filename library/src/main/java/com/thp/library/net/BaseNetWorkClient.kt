package com.thp.library.net

import com.thp.library.BuildConfig
import com.thp.library.net.interceptor.DataCoverter
import com.thp.library.net.interceptor.HeaderInterceptor
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * @author thp
 * time 2020/4/15
 * desc:网路请求基类
 */
abstract class BaseNetWorkClient {
    companion object {
        private const val TIME_OUT = 10L
    }

    private val netClient: OkHttpClient
        get() {
            val builder = RetrofitUrlManager.getInstance().with(OkHttpClient.Builder())
            //添加日志拦截
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }
            builder.run {
                //添加日志
                addInterceptor(logging)
                //添加头部参数
                addInterceptor(HeaderInterceptor())
                //连接时间
                connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                //读取时间
                readTimeout(TIME_OUT, TimeUnit.SECONDS)
                //写入时间
                writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                // 允许重定向
                followRedirects(true)
                // https支持
                SSLSocketClient.getSSLSocketFactory()?.run {
                    sslSocketFactory(sslSocketFactory, trustManager)
                }
                handleBuilder(builder)
            }
            return builder.build()
        }

    /**
     * 以便对builder可以再扩展
     */
    abstract fun handleBuilder(builder: OkHttpClient.Builder)

    open fun <api> getService(serviceClass: Class<api>): api {
        return Retrofit.Builder().client(netClient).baseUrl(NetConfig.BASE_URL)
            .addConverterFactory(DataCoverter.create()).build()
            .create(serviceClass)

    }
}