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
import com.tw.wan.data.viewmodels.ProjectVM
import com.tw.wan.data.viewmodels.ProjectVMFactory
import com.tw.wan.databinding.FragmentArticleProjectBinding
import com.tw.wan.itembinder.ProjectItemBinder

/**
 * @author thp
 * time 2020/5/8
 * desc:
 */
class ProjectArticleFragment : BaseFragment<FragmentArticleProjectBinding>() {
    companion object {
        fun newInstance(id: Int): Fragment {
            val bundle = Bundle()
            bundle.putInt("id", id)
            val fragment = ProjectArticleFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val cid: Int by lazy { arguments?.getInt("id") ?: -1 }
    override fun getContentLayoutId(): Int = R.layout.fragment_article_project
    private val model: ProjectVM by viewModels {
        ProjectVMFactory
    }
    private val mAdapter by lazy {
        BaseBinderAdapter()
    }

    override fun initView() {
        mAdapter.addItemBinder(Article::class.java, ProjectItemBinder())
        binding?.rvList?.adapter = mAdapter
    }

    override fun initData() {
        binding?.model = model
        model.apply {
            getProjectArticles(cid)
            getObserveData(loadStatus) {
                it as ArticlePage
                mAdapter.addData(it.datas)
            }
            refreshStatus.observe(this@ProjectArticleFragment, Observer {
                refreshOrLoadMore(it, cid)
            })

        }
    }
}