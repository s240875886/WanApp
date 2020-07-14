package com.thp.library.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thp.library.net.HttpException
import com.thp.library.net.RequestState
import kotlinx.coroutines.*

/**
 * @author thp
 * time 2020/4/14
 * desc:
 */
open class BaseViewModel : ViewModel() {
    var isFirst = MutableLiveData<Boolean>().apply {
        value = true
    }
    var loadStatus = MutableLiveData<RequestState<Any>>()
    var pageNo = 1
    var pageCount = 1

    // 0刷新中  1刷新完毕  2加载更多中  3加载完成
    val refreshStatus = MutableLiveData(-1)

    /**
     * @param tryBlock 尝试执行的挂起代码块
     * @param catchBlock 捕获异常的代码块 "协程对Retrofit的实现在失败、异常时没有onFailure的回调而是直接已Throwable的形式抛出"
     * @param finallyBlock finally代码块
     */
    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(e: Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                withContext(Dispatchers.IO) {
                    tryBlock()
                }
            } catch (e: Throwable) {
                if (e.message.equals("Job was cancelled")) {

                } else {
                    catchBlock(e)

                }

                if (e is HttpException) {//此异常是网络请求成功，但是服务器有返回错误信息 例如登录失败 服务器返回error 密码错误的提示
                } else {

                }
                e.printStackTrace()

            } finally {
                finallyBlock()
            }
        }
    }

    /**
     * 在IO线程中开启,修改为Dispatchers.IO
     */
    fun launchOnIO(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(e: Throwable) -> Unit = {},
        finallyBlock: suspend CoroutineScope.() -> Unit = {}
    ) {
        viewModelScope.launch {
            tryCatch(tryBlock, catchBlock, finallyBlock)
        }
    }

    open fun refreshOrLoadMore(status: Int) {

    }
}