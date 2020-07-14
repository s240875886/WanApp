package com.tw.wan.activity

import android.content.Intent
import androidx.activity.viewModels
import com.blankj.utilcode.util.KeyboardUtils
import com.thp.library.base.BaseActivity
import com.thp.library.utils.clickDelay
import com.tw.wan.R
import com.tw.wan.data.viewmodels.LoginVM
import com.tw.wan.data.viewmodels.LoginVMFactory
import com.tw.wan.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override fun getContentLayoutId(): Int = R.layout.activity_login
    val model: LoginVM by viewModels {
        LoginVMFactory
    }

    override fun initData() {
        binding.model = model
        getObserveData(model.loginStatus, refreshStat = model.refreshStatus) {
            //登陆成功
            finish()
        }
        btn_login.clickDelay(1000) {
            KeyboardUtils.hideSoftInput(this)
            model.loginUser()
        }
        jump_register.clickDelay(1000) {
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

}