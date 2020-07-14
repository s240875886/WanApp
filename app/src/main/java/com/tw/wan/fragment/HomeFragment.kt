package com.tw.wan.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.chad.library.adapter.base.BaseBinderAdapter
import com.thp.library.base.BaseFragment
import com.tw.wan.R
import com.tw.wan.adapter.ImageNetAdapter
import com.tw.wan.bean.Article
import com.tw.wan.bean.BannerBean
import com.tw.wan.data.viewmodels.CollectFactory
import com.tw.wan.data.viewmodels.CollectVM
import com.tw.wan.data.viewmodels.HomeVM
import com.tw.wan.data.viewmodels.HomeVMFactory
import com.tw.wan.databinding.FragmentHomeBinding
import com.tw.wan.itembinder.HomeItemBinder
import com.tw.wan.lifecycle.BannerLifeCycle
import com.youth.banner.config.BannerConfig
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.util.BannerUtils


/**
 * @author thp
 * time 2020/4/15
 * desc:首页
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getContentLayoutId(): Int = R.layout.fragment_home
    private val collectionModel: CollectVM by viewModels {
        CollectFactory
    }

    companion object {
        fun getInstance(): HomeFragment? {
            return HomeFragment()
        }
    }

    private val mAdapter by lazy {
        BaseBinderAdapter()
    }
    lateinit var banner: BannerLifeCycle
    private val viewModel: HomeVM by viewModels {
        HomeVMFactory

    }

    fun setBannerData(bannerlist: List<BannerBean>) {


        //配置banner
        banner.apply {
            adapter = ImageNetAdapter(activity, bannerlist)
            indicator = CircleIndicator(activity)
            setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
            setIndicatorMargins(
                IndicatorConfig.Margins(
                    0, 0,
                    BannerConfig.INDICATOR_MARGIN, BannerUtils.dp2px(12F).toInt()
                )
            )
        }
    }

    override fun initData() {
        binding?.model = viewModel
        mAdapter.apply {
            addItemBinder(
                Article::class.java, HomeItemBinder(requireActivity(), collectionModel)
            )
            //添加头部banner
            val headView: View =
                layoutInflater.inflate(R.layout.home_banner, null, false)
            banner = headView.findViewById(R.id.banner)
            lifecycle.addObserver(banner)
            addHeaderView(headView)
        }
        binding?.rvList.let {
            it?.adapter = mAdapter
            //防止点击刷新单个item的时候会由于 默认设置的动画造成闪烁
            (it?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        viewModel.run {
            getReferData()
            getObserveData(loadBanner) {
                var data = it as List<BannerBean>
                setBannerData(data)
            }
            getObserveData(loadTop) {
                var data = it as List<Article>
                mAdapter.addData(data)
            }
            getObserveData(loadStatus) {
                var data = it as List<Article>
                mAdapter.addData(data)
            }
            refreshStatus.observe(this@HomeFragment, Observer {
                refreshOrLoadMore(it)
            })

        }


    }

    override fun onRetry() {
        viewModel.isFirst.value = true
        viewModel.getReferData()
    }
}