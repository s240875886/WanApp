package com.tw.wan.data.repository

import com.tw.wan.App

/**
 * @author thp
 * time 2020/5/12
 * desc:
 */
class WxArticleRepository {
    companion object {
        fun getInstance(): WxArticleRepository =
            Holder.mInstance
    }

    private object Holder {
        val mInstance = WxArticleRepository()
    }

    private val service by lazy { App.wanApi }
    suspend fun getWxArticleTab() = service.getWxArticleTab()
    suspend fun getWxArticleList(cid: Int, pageNo: Int) = service.getWxArticleList(cid, pageNo)
}