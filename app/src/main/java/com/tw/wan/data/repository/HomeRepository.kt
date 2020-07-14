package com.tw.wan.data.repository

import com.tw.wan.App

/**
 * @author thp
 * time 2020/4/25
 * desc:
 */
class HomeRepository private constructor() {
    companion object {
        fun getInstance(): HomeRepository =
            Holder.mInstance
    }

    private object Holder {
        val mInstance = HomeRepository()
    }

    private val service by lazy { App.wanApi }
    suspend fun getBanner() = service.getBanner()
    suspend fun getArticleList(pageNo: Int) = service.getArticleList(pageNo)
    suspend fun getTopArticleList() = service.getTopArticleList()

}