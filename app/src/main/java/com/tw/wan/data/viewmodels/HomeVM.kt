package com.tw.wan.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.thp.library.base.BaseViewModel
import com.thp.library.net.RequestState
import com.tw.wan.data.repository.HomeRepository

/**
 * @author thp
 * time 2020/4/25
 * desc:
 */
class HomeVM(private val homeRepository: HomeRepository) : BaseViewModel() {
    var loadBanner = MutableLiveData<RequestState<Any>>()
    var loadTop = MutableLiveData<RequestState<Any>>()

    fun getReferData() {
        pageNo = 1
        getBanner()
        getTopAricle()
        getArticle()
    }

    //获取banner数据
    fun getBanner() {
        launchOnIO(tryBlock = {
            var data = homeRepository.getBanner()
            loadBanner.postValue(RequestState.success(data.data))

        }, catchBlock = { e ->
            ToastUtils.showShort(e.message)
        })

    }

    /**
     *获取文章列表
     */
    fun getArticle() {
        pageNo = 0
        loadStatus.value = RequestState.loading()
        launchOnIO(tryBlock = {

            var data = homeRepository.getArticleList(pageNo)
            pageCount = data?.data?.pageCount!!
            loadStatus.postValue(RequestState.success(data.data?.datas))
        }, catchBlock = { e ->
            ToastUtils.showShort(e.message)
            loadStatus.postValue(RequestState.error(e.message))
        }, finallyBlock = {
            isFirst.value = false
        })

    }

    /**
     * 获取置顶列表
     */
    fun getTopAricle() {
        launchOnIO(tryBlock = {

            var data = homeRepository.getTopArticleList()
            data?.data?.forEach {
                it.top = true
            }
            loadTop.postValue(RequestState.success(data.data))

        }, catchBlock = { e ->
            ToastUtils.showShort(e.message)
            loadTop.postValue(RequestState.error(e.message))
        })
    }

    fun getMoreData() {
        if (pageNo < pageCount) {
            pageNo++
        } else {
            refreshStatus.value = 4
            return
        }
        getArticle()

    }

    override fun refreshOrLoadMore(status: Int) {
        when (status) {
            //下拉刷新
            0 -> {
                getReferData()
                refreshStatus.value = 1
            }
            //上拉加载
            2 -> {
                getMoreData()
                refreshStatus.value = 3

            }
        }
    }

}

object HomeVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeVM(HomeRepository.getInstance()) as T
    }

}