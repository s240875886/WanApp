package com.tw.wan.activity

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseBinderAdapter
import com.thp.library.base.BaseActivity
import com.tw.wan.R
import com.tw.wan.bean.RankItem
import com.tw.wan.data.viewmodels.RankFactory
import com.tw.wan.data.viewmodels.RankVM
import com.tw.wan.databinding.ActivityRankBinding
import com.tw.wan.itembinder.RankItemBinder

class RankActivity : BaseActivity<ActivityRankBinding>() {

    override fun getContentLayoutId(): Int = R.layout.activity_rank
    val model: RankVM by viewModels {
        RankFactory
    }
    val mAdapter by lazy {
        BaseBinderAdapter()
    }

    override fun initView() {
        mAdapter.addItemBinder(RankItem::class.java, RankItemBinder())
        binding.rvList.let {
            it.adapter = mAdapter
        }
    }

    override fun initData() {
        super.initData()
        binding.model = model
        model.run {
            getRank()
            getPersonRank()
            getObserveData(loadStatus, refreshStatus) {
                it as List<RankItem>
                if (pageNo == 1) {
                    mAdapter.setList(it)
                } else {
                    mAdapter.addData(it)
                }
            }
            getObserveData(loadPerson) {
                it as RankItem
                binding.item = it
            }
            refreshStatus.observe(this@RankActivity, Observer {
                refreshOrLoadMore(it)
            })
        }

    }
}