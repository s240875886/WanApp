package com.tw.wan.data.viewmodels

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.thp.library.base.BaseViewModel
import com.thp.library.net.RequestState
import com.tw.wan.R
import com.tw.wan.data.repository.LoginRepository
import com.tw.wan.event.MainEvent
import com.tw.wan.other.Constants
import org.greenrobot.eventbus.EventBus

/**
 * @author thp
 * time 2020/6/1
 * desc:
 */
class LoginVM(private val loginRepository: LoginRepository) : BaseViewModel() {
    var loginStatus = MutableLiveData<RequestState<Any>>()

    //注册或登陆账号
    var userName: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = ""
    }

    //注册或密码
    var password: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = ""
    }

    //注册再次密码
    var repassword: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = ""
    }

    /**
     * 登陆账号 并保存用户信息
     */
    fun loginUser() {
        val userName = userName.value ?: ""
        val password = password.value ?: ""
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.showShort(R.string.hint_user_name)
            return
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort(R.string.hint_password)
            return
        }
        loginStatus.postValue(RequestState.loading())
        launchOnIO(tryBlock = {
            var data = loginRepository.loginUser(userName, password)
            loginStatus.postValue(RequestState.success(data.data))
            ToastUtils.showShort(R.string.login_suc)
            //设置已经登陆
            Constants.isLogin = true
            //本地保存用户信息，用cookdie登陆的时候可以取值
            SPUtils.getInstance().put(Constants.NICK_NAME, data.data?.nickname)
            SPUtils.getInstance().put(Constants.USER_NAME, data.data?.username)
            //更新用户信息
            EventBus.getDefault().post(MainEvent())
        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loginStatus.postValue(RequestState.error(it.message))
        })
    }

    /**
     * 注册账号并登陆
     */
    fun registerUser() {
        val userName = userName.value ?: ""
        val password = password.value ?: ""
        val repassword = repassword.value ?: ""
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.showShort(R.string.hint_user_name)
            return
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort(R.string.hint_password)
            return
        }
        if (TextUtils.isEmpty(repassword)) {
            ToastUtils.showShort(R.string.hint_password_again)
            return
        }
        if (password != repassword) {
            ToastUtils.showShort(R.string.two_password_not_same)
            return
        }
        loadStatus.postValue(RequestState.loading())
        launchOnIO(tryBlock = {
            var data = loginRepository.registerUser(userName, password, repassword)
            loadStatus.postValue(RequestState.success(data.data))
            ToastUtils.showShort(R.string.regis_scu)
            loginUser()
        }, catchBlock = {
            ToastUtils.showShort(it.message)
            loadStatus.postValue(RequestState.error(it.message))
        })
    }

}

object LoginVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginVM(LoginRepository.getInstance()) as T
    }

}