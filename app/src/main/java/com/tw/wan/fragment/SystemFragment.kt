package com.tw.wan.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseBinderAdapter
import com.thp.library.base.BaseFragment
import com.thp.library.utils.UniversalItemDecoration
import com.tw.wan.R
import com.tw.wan.bean.SystemBean
import com.tw.wan.data.viewmodels.SystemVM
import com.tw.wan.data.viewmodels.SystemVMFactory
import com.tw.wan.databinding.FragmentSystemBinding
import com.tw.wan.itembinder.SystemItemBinder

/**
 * @author thp
 * time 2020/4/15
 * desc:综合
 */
class SystemFragment : BaseFragment<FragmentSystemBinding>() {
    override fun getContentLayoutId(): Int = R.layout.fragment_system

    companion object {
        fun getInstance(): SystemFragment? {
            return SystemFragment()
        }
    }

    private val mAdapter by lazy {
        BaseBinderAdapter()
    }
    private val viewModel: SystemVM by viewModels {
        SystemVMFactory

    }


    override fun initData() {
        binding?.model = viewModel
        binding?.refresh?.setEnableLoadMore(false)
        mAdapter.addItemBinder(SystemBean::class.java, SystemItemBinder())
        //瀑布流 加了有缩放的动画 所以上下间隔会不一样 去除动画效果 则上下间距能够一样
        binding?.rvList?.run {
            addItemDecoration(object : UniversalItemDecoration() {
                override fun getItemOffsets(position: Int): Decoration? {
                    return ColorDecoration().apply {
                        if (position % 2 == 0) {
                            left = ConvertUtils.dp2px(8f)
                            right = ConvertUtils.dp2px(8f)
                        } else {
                            right = ConvertUtils.dp2px(8f)
                        }
                        top = ConvertUtils.dp2px(8f)
                        bottom = ConvertUtils.dp2px(8f)
                    }
                }
            })
            layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
                    gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
                }
            adapter = mAdapter
        }
        viewModel.run {
            getSystemData()
            getObserveData(loadStatus) {
                var data = it as List<SystemBean>
                mAdapter.addData(data)
            }
            refreshStatus.observe(this@SystemFragment, Observer {
                refreshOrLoadMore(it)
            })

        }


    }

    override fun onRetry() {
        viewModel.isFirst.value = true
        viewModel.getSystemData()
    }
}