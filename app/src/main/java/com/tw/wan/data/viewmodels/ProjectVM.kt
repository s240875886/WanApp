package com.tw.wan.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.thp.library.base.BaseViewModel
import com.thp.library.net.RequestState
import com.tw.wan.data.repository.ProjectRepository

/**
 * @author thp
 * time 2020/5/8
 * desc:
 */
class ProjectVM(private val projectRepository: ProjectRepository) : BaseViewModel() {
    var projectTopData = MutableLiveData<RequestState<Any>>()

    fun getProjects() {
        launchOnIO(
            tryBlock = {
                val data = projectRepository.getProjects()
                projectTopData.postValue(RequestState.success(data.data))
            },
            catchBlock = {
                ToastUtils.showShort(it.message)
                projectTopData.postValue(RequestState.error(it.message))
            })
    }

    fun getProjectArticles(cid: Int) {
        loadStatus.value = RequestState.loading()
        launchOnIO(
            tryBlock = {
                val data = projectRepository.getProjectArticles(pageNo, cid)
                pageCount = data?.data?.pageCount!!
                loadStatus.postValue(RequestState.success(data.data))
            },
            catchBlock = {
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
                getProjectArticles(cid)
                refreshStatus.value = 1
            }
            2 -> {
                if (pageNo < pageCount) {
                    pageNo++
                } else {
                    refreshStatus.value = 4
                    return
                }
                getProjectArticles(cid)
                refreshStatus.value = 3
            }
        }
    }
}

object ProjectVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectVM(ProjectRepository.getInstance()) as T
    }

}