package com.tw.wan.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thp.library.base.BaseFragment
import com.tw.wan.R
import com.tw.wan.adapter.ProjectTabAdapter
import com.tw.wan.bean.Wxarticle
import com.tw.wan.data.viewmodels.WxVM
import com.tw.wan.data.viewmodels.WxVMFactory
import com.tw.wan.databinding.FragmentProjectBinding
import com.tw.wan.databinding.FragmentWxBinding

/**
 * @author thp
 * time 2020/5/8
 * desc:
 */
class WxFragment : BaseFragment<FragmentWxBinding>() {
    override fun getContentLayoutId(): Int = R.layout.fragment_wx

    companion object {
        fun getInstance(): WxFragment? {
            return WxFragment()
        }
    }

    val fragments by lazy { arrayListOf<Fragment>() }
    val tabs by lazy { arrayListOf<String>() }
    private val model: WxVM by viewModels {
        WxVMFactory
    }

    override fun initView() {
    }

    override fun initData() {
        binding?.model = model
        model.apply {
            getWxArticleTab()
            getObserveData(loadStatus) { data ->
                data as List<Wxarticle>
                fragments.clear()
                tabs.clear()
                data.forEach {
                    tabs.add(it.name)
                    fragments.add(WxArticleFragment.newInstance(it.id))
                }
                binding?.viewPage?.adapter =
                    ProjectTabAdapter(childFragmentManager, tabs, fragments)
                binding?.tab?.setViewPager(binding?.viewPage)
            }
        }
    }
}