package com.tw.wan.activity

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseBinderAdapter
import com.thp.library.base.BaseActivity
import com.tw.wan.R
import com.tw.wan.bean.Article
import com.tw.wan.data.viewmodels.*
import com.tw.wan.databinding.ActivitySystemListBinding
import com.tw.wan.itembinder.SystemListItemBinder
import com.tw.wan.other.Constants

class SystemListActivity : BaseActivity<ActivitySystemListBinding>() {
    var cid: Int = 0
    override fun getContentLayoutId(): Int = R.layout.activity_system_list
    private val model: SystemListVM by viewModels {
        SystemListFactory
    }
    private val mAdapter by lazy {
        BaseBinderAdapter()
    }

    override fun initView() {
        binding.model = model
        mAdapter.addItemBinder(Article::class.java, SystemListItemBinder())
        binding.rvList.adapter = mAdapter

    }

    override fun initData() {
        var titleName = intent.getStringExtra(Constants.TITLE_NAME)
        cid = intent.getIntExtra(Constants.CID, 0)
        binding.titleBar.title = titleName
        model.apply {
            getSystemListData(cid)
            getObserveData(this.loadStatus) {
                var datas = it as List<Article>
                if (pageNo == 1) {
                    mAdapter.setList(datas)
                } else {
                    mAdapter.addData(datas)
                }

            }
            refreshStatus.observe(this@SystemListActivity, Observer {
                refreshOrLoadMore(it, cid)
            })
        }

    }

}
