package com.tw.wan.activity

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseBinderAdapter
import com.thp.library.base.BaseActivity
import com.tw.wan.R
import com.tw.wan.bean.Article
import com.tw.wan.data.viewmodels.CollectFactory
import com.tw.wan.data.viewmodels.CollectVM
import com.tw.wan.databinding.ActivityCollectBinding
import com.tw.wan.itembinder.CollectListItemBinder

class CollectActivity : BaseActivity<ActivityCollectBinding>() {

    override fun getContentLayoutId(): Int = R.layout.activity_collect
    private val model: CollectVM by viewModels {
        CollectFactory
    }
    private val mAdapter by lazy {
        BaseBinderAdapter()
    }

    override fun initView() {
        binding.model = model
        mAdapter.addItemBinder(Article::class.java, CollectListItemBinder(this, model))
        binding.rvList.adapter = mAdapter

    }

    override fun initData() {
        model.apply {
            getCollectList()
            getObserveData(this.loadStatus, refreshStatus) {
                var datas = it as List<Article>
                if (pageNo === 0) {
                    mAdapter.setList(datas)
                } else {
                    mAdapter.addData(datas)
                }
                dimissLoadingDialog()
            }
            refreshStatus.observe(this@CollectActivity, Observer {
                refreshOrLoadMore(it)
            })
        }

    }

    override fun onRetry() {
        model.isFirst.value = true
        model.getCollectList()
    }

}
