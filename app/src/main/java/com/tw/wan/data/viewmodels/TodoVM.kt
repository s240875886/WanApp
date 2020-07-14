package com.tw.wan.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.thp.library.base.BaseViewModel
import com.thp.library.net.RequestState
import com.tw.wan.bean.TodoItem
import com.tw.wan.data.repository.TodoRepository
import com.tw.wan.other.Constants

/**
 * @author thp
 * time 2020/5/6
 * desc:
 */
class TodoVM(private val todoRepository: TodoRepository) : BaseViewModel() {
    var item = MutableLiveData<TodoItem>().apply {
        value = TodoItem()
    }
    var myStat = 0
    fun loadTodoData(status: Int) {
        myStat = status
        var map = hashMapOf<String, Any>().apply {
            put("status", status)
        }
        loadStatus.value = RequestState.loading()
        launchOnIO(tryBlock = {
            var data = todoRepository.loadTodoData(pageNo, map)
            pageCount = data.data?.pageCount!!
            loadStatus.postValue(RequestState.success(data.data?.datas))

        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadStatus.postValue(RequestState.error(it.message))
        }, finallyBlock = {
            isFirst.postValue(false)
        })
    }

    fun deleteTodo(id: Int) {
        launchOnIO(tryBlock = {
            var data = todoRepository.deleteTodo(id)
            loadStatus.postValue(RequestState.success(Constants.TODO_DEL))

        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadStatus.postValue(RequestState.error(it.message))
        })
    }

    fun doneTodo(id: Int, status: Int) {
        launchOnIO(tryBlock = {
            var data = todoRepository.doneTodo(id, status)
            if (status == 1) {
                loadStatus.postValue(RequestState.success(Constants.TODO_DONE))
            } else {
                loadStatus.postValue(RequestState.success(Constants.TODO_BACK))
            }


        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadStatus.postValue(RequestState.error(it.message))
        })
    }

    fun updateTodo(id: Int) {
        item.value?.let {
            if (it.title.isNullOrEmpty()) {
                ToastUtils.showShort("标题不能为空")
                return
            }
            if (it.content.isNullOrEmpty()) {
                ToastUtils.showShort("详情不能为空")
                return
            }
            if (it.dateStr.isNullOrEmpty()) {
                ToastUtils.showShort("请选择时间")
                return
            }
        }
        var map = hashMapOf<String, Any>().apply {
            item.value?.let {
                put("title", it.title.toString())
                put("content", it.content.toString())
                put("date", it.dateStr.toString())
            }
        }
        launchOnIO(tryBlock = {
            var data = todoRepository.updateTodo(id, map)
            loadStatus.postValue(RequestState.success(Constants.TODO_UPDATAE))
        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadStatus.postValue(RequestState.error(it.message))
        })
    }

    fun addTodo() {
        item.value?.let {
            if (it.title.isNullOrEmpty()) {
                ToastUtils.showShort("标题不能为空")
                return
            }
            if (it.content.isNullOrEmpty()) {
                ToastUtils.showShort("详情不能为空")
                return
            }
            if (it.dateStr.isNullOrEmpty()) {
                ToastUtils.showShort("请选择时间")
                return
            }
        }
        var map = hashMapOf<String, Any>().apply {
            item.value?.let {
                put("title", it.title.toString())
                put("content", it.content.toString())
                put("date", it.dateStr.toString())
            }
        }
        launchOnIO(tryBlock = {
            var data = todoRepository.addTodo(map)
            loadStatus.postValue(RequestState.success(0))
        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadStatus.postValue(RequestState.error(it.message))
        })
    }

    override fun refreshOrLoadMore(status: Int) {
        when (status) {
            //下拉刷新
            0 -> {
                pageNo = 1
                loadTodoData(this.myStat)
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
                loadTodoData(this.myStat)
                refreshStatus.value = 3

            }
        }
    }

}

object TodoVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoVM(TodoRepository.getInstance()) as T
    }

}