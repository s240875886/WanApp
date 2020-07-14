package com.tw.wan.data.repository

import com.tw.wan.App

/**
 * @author thp
 * time 2020/6/1
 * desc:
 */
class LoginRepository {
    companion object {
        fun getInstance(): LoginRepository =
            Holder.mInstance
    }

    private object Holder {
        val mInstance = LoginRepository()
    }

    private val service by lazy { App.wanApi }
    suspend fun loginUser(userName: String, password: String) =
        service.loginUser(userName, password)

    suspend fun registerUser(userName: String, password: String, repassword: String) =
        service.registerUser(userName, password, repassword)

}