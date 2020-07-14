package com.tw.wan.data.repository

import com.tw.wan.App

/**
 * @author thp
 * time 2020/5/8
 * desc:
 */
class ProjectRepository {
    companion object {
        fun getInstance(): ProjectRepository =
            Holder.mInstance
    }

    private object Holder {
        val mInstance = ProjectRepository()
    }

    private val service by lazy { App.wanApi }
    suspend fun getProjects() = service.getProjects()
    suspend fun getProjectArticles(pageNum: Int, cid: Int) =
        service.getProjectArticles(pageNum, cid)

}