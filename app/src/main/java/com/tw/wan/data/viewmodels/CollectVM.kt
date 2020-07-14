package com.tw.wan.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.thp.library.base.BaseViewModel
import com.thp.library.net.HttpException
import com.thp.library.net.RequestState
import com.tw.wan.data.repository.CollectRepository
import com.tw.wan.other.Constants

/**
 * @author thp
 * time 2020/6/5
 * desc:
 */
class CollectVM(private val collectRepository: CollectRepository) : BaseViewModel() {
    var collectStatdus = MutableLiveData<RequestState<Any>>()

    /**
     * 获取收藏列表
     */
    fun getCollectList() {
        loadStatus.postValue(RequestState.loading())
        pageNo = 0
        launchOnIO(tryBlock = {
            val data = collectRepository.getCollectList(pageNo)
            pageCount = data.data?.pageCount!!
            loadStatus.postValue(RequestState.success(data.data?.datas))

        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadStatus.postValue(RequestState.error(it.message))
            if (it is HttpException && it.code == Constants.LOGIN_FAILURE) {
                SPUtils.getInstance().clear()
            }
        }, finallyBlock = {
            isFirst.postValue(false)
        })
    }

    /**
     *根据id收藏文章
     */
    fun atCollect(id: Int) {
        launchOnIO(tryBlock = {
            val data = collectRepository.atCollect(id)
            collectStatdus.postValue(RequestState.success(data))

        }, catchBlock = {
            ToastUtils.showShort(it.message)
            collectStatdus.postValue(RequestState.error(it.message))
        })
    }

    /**
     * 根据id取消收藏
     */
    fun unCollect(id: Int) {
        launchOnIO(tryBlock = {
            val data = collectRepository.unCollect(id)
            collectStatdus.postValue(RequestState.success(data))

        }, catchBlock = {
            ToastUtils.showShort(it.message)
            collectStatdus.postValue(RequestState.error(it.message))
        })
    }


    override fun refreshOrLoadMore(status: Int) {
        when (status) {
            //下拉刷新
            0 -> {
                pageNo = 0
                getCollectList()
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
                getCollectList()
                refreshStatus.value = 3

            }
        }
    }
}

object CollectFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CollectVM(CollectRepository.getInstance()) as T
    }
}