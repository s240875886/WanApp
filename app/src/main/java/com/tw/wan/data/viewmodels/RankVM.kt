package com.tw.wan.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.thp.library.base.BaseViewModel
import com.thp.library.net.RequestState
import com.tw.wan.data.repository.RankRepository

/**
 * @author thp
 * time 2020/6/16
 * desc:
 */
class RankVM(private val rankRepository: RankRepository) : BaseViewModel() {
    var loadPerson = MutableLiveData<RequestState<Any>>()
    fun getRank() {
        loadStatus.postValue(RequestState.loading())
        launchOnIO(tryBlock = {
            val data = rankRepository.getRank(pageNo)
            pageCount = data.data?.pageCount!!
            loadStatus.postValue(RequestState.success(data.data?.datas))

        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadStatus.postValue(RequestState.error(it.message))
        }, finallyBlock = {
            isFirst.postValue(false)
        })
    }

    fun getPersonRank() {
//        loadPerson.postValue(RequestState.loading())
        launchOnIO(tryBlock = {
            val data = rankRepository.getPersonRank()
            loadPerson.postValue(RequestState.success(data?.data))

        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadPerson.postValue(RequestState.error(it.message))
        })
    }

    override fun refreshOrLoadMore(status: Int) {
        when (status) {
            //下拉刷新
            0 -> {
                pageNo = 1
                getRank()
                getPersonRank()
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
                getRank()
                getPersonRank()
                refreshStatus.value = 3

            }
        }
    }
}

object RankFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RankVM(RankRepository.getInstance()) as T
    }

}
