package com.tw.wan.activity

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseBinderAdapter
import com.thp.library.base.BaseActivity
import com.tw.wan.R
import com.tw.wan.bean.Article
import com.tw.wan.data.viewmodels.HotVM
import com.tw.wan.data.viewmodels.HotVMFactory
import com.tw.wan.databinding.ActivitySearchListBinding
import com.tw.wan.itembinder.SearchListItemBinder
import com.tw.wan.other.Constants

class SearchListActivity : BaseActivity<ActivitySearchListBinding>() {

    override fun getContentLayoutId(): Int = R.layout.activity_search_list
    val titleName by lazy {
        intent.getStringExtra(Constants.TITLE_NAME)
    }
    private val model: HotVM by viewModels {
        HotVMFactory
    }
    private val mAdapter by lazy {
        BaseBinderAdapter()
    }

    override fun initView() {
        binding.model = model
        mAdapter.addItemBinder(Article::class.java, SearchListItemBinder())
        binding.rvList.adapter = mAdapter

    }

    override fun initData() {
        binding.titleBar.title = titleName
        model.apply {
            getSearchData(titleName)
            getObserveData(this.loadStatus) {
                var datas = it as List<Article>
                if (pageNo === 1) {
                    mAdapter.setList(datas)
                } else {
                    mAdapter.addData(datas)
                }

            }
            refreshStatus.observe(this@SearchListActivity, Observer {
                refreshOrLoadMore(it, titleName)
            })
        }

    }

    override fun onRetry() {
        model.isFirst.value = true
        model.getSearchData(titleName)
    }

}
