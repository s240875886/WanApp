package com.tw.wan.data.repository

import com.tw.wan.App

/**
 * @author thp
 * time 2020/6/16
 * desc:
 */
class RankRepository {
    companion object {
        fun getInstance(): RankRepository =
            Holder.mInstance
    }

    private object Holder {
        val mInstance = RankRepository()
    }

    private val service by lazy { App.wanApi }
    suspend fun getRank(pageNo: Int) = service.getRank(pageNo)
    suspend fun getPersonRank() = service.getPersonRank()
}