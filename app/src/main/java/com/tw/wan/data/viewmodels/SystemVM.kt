package com.tw.wan.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.thp.library.base.BaseViewModel
import com.thp.library.net.RequestState
import com.tw.wan.data.repository.SystemRepository

/**
 * @author thp
 * time 2020/5/5
 * desc:
 */
class SystemVM(private val systemRepository: SystemRepository) : BaseViewModel() {


    fun getSystemData() {
        loadStatus.value = RequestState.loading()
        launchOnIO(
            tryBlock = {
                var data = systemRepository.getSystemData()
                loadStatus.postValue(RequestState.success(data.data))
            }, catchBlock = { it ->
                ToastUtils.showShort(it.message)
                loadStatus.postValue(RequestState.error(it.message))
            }, finallyBlock = {
                isFirst.postValue(false)
            }
        )
    }

    override fun refreshOrLoadMore(status: Int) {
        if (status == 0) {
            getSystemData()
            refreshStatus.value = 1
        }
    }
}

object SystemVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SystemVM(SystemRepository.getInstance()) as T
    }


}
