package com.tw.wan.activity

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.thp.library.base.BaseActivity
import com.tw.wan.R
import com.tw.wan.databinding.ActivityWebBinding
import com.tw.wan.other.Constants


/**
 * @author thp
 * time 2020/4/30
 * desc:
 */
class WebActivity : BaseActivity<ActivityWebBinding>() {
    companion object {
        fun openWebAcitivty(activity: Context, url: String, title: String) {
            activity.startActivity(Intent(activity, WebActivity::class.java).apply {
                putExtra(Constants.WEB_URL, url)
                putExtra(Constants.WEB_TITLE, title)
            })
        }
    }

    private val url by lazy {
        intent.getStringExtra(Constants.WEB_URL)
    }
    private val title by lazy {
        intent.getStringExtra(Constants.WEB_TITLE)
    }

    override fun getContentLayoutId(): Int = R.layout.activity_web
    override fun getTitleId(): Int = R.id.web_title
    private lateinit var mAgentWeb: AgentWeb

    override fun initData() {

        binding.tvTitle.apply {
            text = title
            //android 10 不加这句话 会不滚动
            isSelected = true
        }
        binding.ivback.setOnClickListener {
            finish()
        }
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.container, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(url)

    }

    override fun onBackPressed() {
        mAgentWeb.run {
            if (!back()) {
                super.onBackPressed()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else {
            finish()
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle?.onDestroy()
        super.onDestroy()
    }
}