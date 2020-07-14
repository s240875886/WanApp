package com.tw.wan.activity

import android.content.Intent
import androidx.activity.viewModels
import com.thp.library.base.BaseActivity
import com.tw.wan.R
import com.tw.wan.data.viewmodels.LoginVM
import com.tw.wan.data.viewmodels.LoginVMFactory
import com.tw.wan.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    override fun getContentLayoutId(): Int = R.layout.activity_register
    val model: LoginVM by viewModels {
        LoginVMFactory
    }

    override fun initData() {
        binding.model = model
        model.run {
            getObserveData(loadStatus) {
                //注册成功关闭
                finish()
            }
        }
        jump_login.setOnClickListener {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

}
