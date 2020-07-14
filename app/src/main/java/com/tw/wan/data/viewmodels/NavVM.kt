package com.tw.wan.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.thp.library.base.BaseViewModel
import com.thp.library.net.RequestState
import com.tw.wan.data.repository.NavRepository

/**
 * @author thp
 * time 2020/5/7
 * desc:
 */
class NavVM(private val navRepository: NavRepository) : BaseViewModel() {

    fun getNavigationData() {
        loadStatus.value = RequestState.loading()
        launchOnIO(tryBlock = {
            val data = navRepository.getNavigationData()
            loadStatus.postValue(RequestState.success(data.data))

        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadStatus.postValue(RequestState.error(it.message))
        }, finallyBlock = {
            isFirst.postValue(false)
        })
    }

    override fun refreshOrLoadMore(status: Int) {
        if (status == 0) {
            getNavigationData()
            refreshStatus.value = 1
        }

    }
}

object NavVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NavVM(NavRepository.getInstance()) as T
    }

}