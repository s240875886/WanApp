package com.tw.wan.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.thp.library.base.BaseViewModel
import com.thp.library.net.RequestState
import com.tw.wan.data.repository.SystemRepository

/**
 * @author thp
 * time 2020/5/6
 * desc:
 */
class SystemListVM(private val systemRepository: SystemRepository) : BaseViewModel() {
    fun getSystemListData(id: Int) {
        loadStatus.value = RequestState.loading()
        launchOnIO(tryBlock = {
            var data = systemRepository.getSystemListData(pageNo, id)
            pageCount = data.data?.pageCount!!
            loadStatus.postValue(RequestState.success(data.data?.datas))

        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadStatus.postValue(RequestState.error(it.message))
        }, finallyBlock = {
            isFirst.postValue(false)
        })
    }

    fun refreshOrLoadMore(status: Int, id: Int) {
        when (status) {
            //下拉刷新
            0 -> {
                pageNo = 1
                getSystemListData(id)
                refreshStatus.value = 1
            }
            //上拉加载
            2 -> {
                if (pageNo < pageCount) {
                    pageNo++
                } else {
                    refreshStatus.value = 4
                    return
                }
                getSystemListData(id)
                refreshStatus.value = 3

            }
        }
    }

}

object SystemListFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SystemListVM(SystemRepository.getInstance()) as T
    }

}