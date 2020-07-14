package com.tw.wan.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseBinderAdapter
import com.thp.library.base.BaseFragment
import com.tw.wan.R
import com.tw.wan.bean.Article
import com.tw.wan.bean.ArticlePage
import com.tw.wan.data.viewmodels.WxVM
import com.tw.wan.data.viewmodels.WxVMFactory
import com.tw.wan.databinding.FragmentArticleProjectBinding
import com.tw.wan.databinding.FragmentArticleWxBinding
import com.tw.wan.itembinder.WxItemBinder

/**
 * @author thp
 * time 2020/5/8
 * desc:
 */
class WxArticleFragment : BaseFragment<FragmentArticleWxBinding>() {
    companion object {
        fun newInstance(id: Int): Fragment {
            val bundle = Bundle()
            bundle.putInt("id", id)
            val fragment = WxArticleFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val cid: Int by lazy { arguments?.getInt("id") ?: -1 }
    override fun getContentLayoutId(): Int = R.layout.fragment_article_wx
    private val model: WxVM by viewModels {
        WxVMFactory
    }
    private val mAdapter by lazy {
        BaseBinderAdapter()
    }

    override fun initView() {
        mAdapter.addItemBinder(Article::class.java, WxItemBinder())
        binding?.rvList?.adapter = mAdapter
    }

    override fun initData() {
        binding?.model = model
        model.apply {
            getWxArticleList(cid)
            getObserveData(loadStatus) {
                it as ArticlePage
                mAdapter.addData(it.datas)
            }
            refreshStatus.observe(this@WxArticleFragment, Observer {
                refreshOrLoadMore(it, cid)
            })

        }
    }

}