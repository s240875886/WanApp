package com.tw.wan.data.repository

import com.tw.wan.App

/**
 * @author thp
 * time 2020/5/7
 * desc:
 */
class NavRepository {
    companion object {
        fun getInstance(): NavRepository =
            Holder.mInstance
    }

    private object Holder {
        val mInstance = NavRepository()
    }

    private val service by lazy { App.wanApi }
    suspend fun getNavigationData() = service.getNavigationData()
}