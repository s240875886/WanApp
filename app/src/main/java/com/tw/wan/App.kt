package com.tw.wan

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.thp.library.net.NetConfig
import com.tw.wan.api.RetrofitClient
import com.tw.wan.api.WanApi
import com.tw.wan.other.Constants
import com.tw.wan.utils.NotNUllSingleVar
import me.jessyan.retrofiturlmanager.RetrofitUrlManager

/**
 * @author thp
 * time 2020/4/24
 * desc:
 */
class App : Application() {
    companion object {
        var application by NotNUllSingleVar<App>()
        var httpClient by NotNUllSingleVar<RetrofitClient>()
        var wanApi by NotNUllSingleVar<WanApi>()
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        //配置网络
        initNetConfig()
    }

    private fun initNetConfig() {
        NetConfig.run {
            BASE_URL = Constants.MAINURL
            httpClient = RetrofitClient.instance
            wanApi = httpClient.getService(WanApi::class.java)
//            // 可在 App 运行时,随时切换 BaseUrl (指定了 Domain-Name header 的接口)
//            RetrofitUrlManager.getInstance().putDomain("douban", "https://api.douban.com");
            // 全局 BaseUrl 的优先级低于 Domain-Name header 中单独配置的,其他未配置的接口将受全局 BaseUrl 的影响
            RetrofitUrlManager.getInstance().setGlobalDomain(BASE_URL);
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this);
    }

}