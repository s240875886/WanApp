package com.tw.wan.data.repository

import com.tw.wan.App
import com.tw.wan.data.database.AppDatabase
import com.tw.wan.data.database.bean.Word

/**
 * @author thp
 * time 2020/5/13
 * desc:
 */
class HotRepository {
    companion object {
        fun getInstance(): HotRepository =
            Holder.mInstance
    }

    private object Holder {
        val mInstance = HotRepository()
    }

    private val service by lazy { App.wanApi }
    private val localData by lazy { AppDatabase.getInstance(App.application).wordDao() }
    suspend fun getHotData() = service.getHotData()
    suspend fun getSearchData(pageNo: Int, keyWord: String) = service.getSearchData(pageNo, keyWord)
    suspend fun insert(word: Word) = localData.insert(word)
    suspend fun delete(word: Word) = localData.delete(word)
    suspend fun deleteAll() = localData.deleteAll()
    suspend fun getAllWords() = localData.getAllWords()
}