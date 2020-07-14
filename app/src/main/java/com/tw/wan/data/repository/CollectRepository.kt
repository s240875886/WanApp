package com.tw.wan.data.repository

import com.tw.wan.App

/**
 * @author thp
 * time 2020/6/5
 * desc:
 */
class CollectRepository {
    companion object {
        fun getInstance(): CollectRepository =
            Holder.mInstance
    }

    private object Holder {
        val mInstance = CollectRepository()
    }

    private val service by lazy { App.wanApi }
    suspend fun getCollectList(pageNo: Int) = service.getCollectList(pageNo)
    suspend fun atCollect(id: Int) = service.atCollect(id)
    suspend fun unCollect(id: Int) = service.unCollect(id)
}