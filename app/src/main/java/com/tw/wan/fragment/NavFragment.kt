package com.tw.wan.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseBinderAdapter
import com.thp.library.base.BaseFragment
import com.tw.wan.R
import com.tw.wan.bean.NavBean
import com.tw.wan.data.viewmodels.NavVM
import com.tw.wan.data.viewmodels.NavVMFactory
import com.tw.wan.databinding.FragmentNavBinding
import com.tw.wan.itembinder.NavLeftItemBinder
import com.tw.wan.itembinder.NavRightItemBinder

/**
 * @author thp
 * time 2020/5/7
 * desc:
 */
class NavFragment : BaseFragment<FragmentNavBinding>() {
    override fun getContentLayoutId(): Int = R.layout.fragment_nav

    companion object {
        fun getInstance(): NavFragment? {
            return NavFragment()
        }
    }

    val model: NavVM by viewModels {
        NavVMFactory
    }
    val mAdapterLef by lazy {
        BaseBinderAdapter()
    }
    val mAdapterRight by lazy {
        BaseBinderAdapter()
    }

    override fun initView() {
        mAdapterLef.addItemBinder(NavBean::class.java, NavLeftItemBinder(binding?.rvListRight))
        mAdapterRight.addItemBinder(NavBean::class.java, NavRightItemBinder())

    }

    override fun initData() {
        binding?.model = model
        binding?.refresh?.setEnableLoadMore(false)
        binding?.rvListLeft?.adapter = mAdapterLef
        binding?.rvListRight?.adapter = mAdapterRight
        model.apply {
            getNavigationData()
            getObserveData(loadStatus) {
                var data = it as List<NavBean>
                data?.get(0)?.isClick = true
                mAdapterLef.setList(data)
                mAdapterRight.setList(data)
            }
            refreshStatus.observe(this@NavFragment, Observer {
                refreshOrLoadMore(it)
            })
        }
    }

    override fun onRetry() {
        model.isFirst.value = true
        model.getNavigationData()
    }
}