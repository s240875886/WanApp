package com.tw.wan.activity

import android.content.Intent
import android.view.View
import com.hjq.bar.OnTitleBarListener
import com.thp.library.base.BaseActivity
import com.tw.wan.R
import com.tw.wan.adapter.ProjectTabAdapter
import com.tw.wan.databinding.ActivityTodoBinding
import com.tw.wan.event.TodoDone
import com.tw.wan.fragment.TodoListFragment
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class TodoActivity : BaseActivity<ActivityTodoBinding>() {
    val fragments by lazy {
        arrayListOf(
            TodoListFragment.newInstance(0),
            TodoListFragment.newInstance(1)
        )
    }
    val tabs by lazy { arrayListOf("待办", "已完成") }

    override fun getContentLayoutId(): Int = R.layout.activity_todo
    override fun initData() {
        binding.viewPage?.adapter =
            ProjectTabAdapter(supportFragmentManager, tabs, fragments)
        binding.tab.setViewPager(binding?.viewPage)
    }

    override fun initListener() {
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
                finish()
            }

            override fun onRightClick(v: View?) {
                startActivity(Intent(this@TodoActivity, AddTodoActivity::class.java))
            }

            override fun onTitleClick(v: View?) {
            }

        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: TodoDone) {
        if (event.status == 0) {
            binding.viewPage.currentItem = 0
        } else {
            binding.viewPage.currentItem = 1
        }

    }
}