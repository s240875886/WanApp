package com.tw.wan.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.thp.library.base.BaseViewModel
import com.thp.library.net.RequestState
import com.tw.wan.data.database.bean.Word
import com.tw.wan.data.repository.HotRepository

/**
 * @author thp
 * time 2020/5/12
 * desc:
 */
class HotVM(private val hotRepository: HotRepository) :
    BaseViewModel() {
    var historySearchData = MutableLiveData<List<Word>>()

    /**
     * 获取热门搜索
     */
    fun getHotData() {
        loadStatus.postValue(RequestState.loading())
        launchOnIO(tryBlock = {
            val data = hotRepository.getHotData()
            loadStatus.postValue(RequestState.success(data.data))

        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadStatus.postValue(RequestState.error(it.message))
        }, finallyBlock = {})
    }

    /**
     * 根据搜索内容获取列表
     */
    fun getSearchData(keyword: String) {
        loadStatus.postValue(RequestState.loading())
        launchOnIO(tryBlock = {
            val data = hotRepository.getSearchData(pageNo, keyword)
            pageCount = data.data?.pageCount!!
            loadStatus.postValue(RequestState.success(data.data?.datas))

        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadStatus.postValue(RequestState.error(it.message))
        }, finallyBlock = {
            isFirst.postValue(false)
        })
    }

    /**
     * 搜索词插入数据库
     */
    fun insert(word: Word) {
        launchOnIO(tryBlock = {
            hotRepository.insert(word)
        }, catchBlock = {
            ToastUtils.showShort(it.message)
        })
    }

    /**
     * 搜索词从数据库删除
     */
    fun delete(word: Word) {
        launchOnIO(tryBlock = {
            hotRepository.delete(word)
            historySearchData.postValue(hotRepository.getAllWords())
        }, catchBlock = {
            ToastUtils.showShort(it.message)
        })
    }

    /**
     * 清空所有历史搜索词
     */
    fun deleteAll() {
        launchOnIO(tryBlock = {
            hotRepository.deleteAll()
            historySearchData.postValue(listOf())
        }, catchBlock = {
            ToastUtils.showShort(it.message)
        })
    }

    /**
     * 获取所有历史搜索内容
     */
    fun getAllWords() {
        launchOnIO(tryBlock = {
            val data = hotRepository.getAllWords()
            historySearchData.postValue(data)

        }, catchBlock = {
            ToastUtils.showShort(it.message)
        })
    }

    fun refreshOrLoadMore(status: Int, keyword: String) {
        when (status) {
            0 -> {
                pageNo++
                getSearchData(keyword)
                refreshStatus.value = 1
            }
            2 -> {
                if (pageNo < pageCount) {
                    pageNo++
                } else {
                    refreshStatus.value = 4
                    return
                }
                getSearchData(keyword)
                refreshStatus.value = 3
            }
        }
    }
}

object HotVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HotVM(HotRepository.getInstance()) as T
    }

}