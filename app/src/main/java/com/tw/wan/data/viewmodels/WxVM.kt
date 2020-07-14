package com.tw.wan.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.thp.library.base.BaseViewModel
import com.thp.library.net.RequestState
import com.tw.wan.data.repository.WxArticleRepository

/**
 * @author thp
 * time 2020/5/12
 * desc:
 */
class WxVM(private val wxArticleRepository: WxArticleRepository) : BaseViewModel() {
    fun getWxArticleTab() {
        launchOnIO(tryBlock = {
            val data = wxArticleRepository.getWxArticleTab()
            loadStatus.postValue(RequestState.success(data.data))

        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadStatus.postValue(RequestState.error(it.message))
        }, finallyBlock = {})
    }

    fun getWxArticleList(cid: Int) {
        loadStatus.postValue(RequestState.loading())
        launchOnIO(tryBlock = {
            val data = wxArticleRepository.getWxArticleList(cid, pageNo)
            loadStatus.postValue(RequestState.success(data.data))

        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadStatus.postValue(RequestState.error(it.message))
        }, finallyBlock = {
            isFirst.postValue(false)
        })
    }

    fun refreshOrLoadMore(status: Int, cid: Int) {
        when (status) {
            0 -> {
                pageNo++
                getWxArticleList(cid)
                refreshStatus.value = 1
            }
            2 -> {
                if (pageNo < pageCount) {
                    pageNo++
                } else {
                    refreshStatus.value = 4
                    return
                }
                getWxArticleList(cid)
                refreshStatus.value = 3
            }
        }
    }
}

object WxVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WxVM(WxArticleRepository.getInstance()) as T
    }

}