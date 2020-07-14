package com.tw.wan.data.repository

import com.tw.wan.App

/**
 * @author thp
 * time 2020/5/5
 * desc:
 */
class SystemRepository private constructor() {
    companion object {
        fun getInstance(): SystemRepository =
            Holder.mInstance
    }

    private object Holder {
        val mInstance = SystemRepository()
    }

    private val service by lazy { App.wanApi }
    suspend fun getSystemData() = service.getSystemData()
    suspend fun getSystemListData(pageNum: Int, id: Int) = service.getSystemListData(pageNum, id)
}