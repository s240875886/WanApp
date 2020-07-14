package com.tw.wan.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chad.library.adapter.base.BaseBinderAdapter
import com.thp.library.base.BaseFragment
import com.tw.wan.R
import com.tw.wan.adapter.ProjectTabAdapter
import com.tw.wan.bean.ProjectBean
import com.tw.wan.data.viewmodels.ProjectVM
import com.tw.wan.data.viewmodels.ProjectVMFactory
import com.tw.wan.databinding.FragmentProjectBinding

/**
 * @author thp
 * time 2020/5/8
 * desc:
 */
class ProjectFragment : BaseFragment<FragmentProjectBinding>() {
    override fun getContentLayoutId(): Int = R.layout.fragment_project

    companion object {
        fun getInstance(): ProjectFragment? {
            return ProjectFragment()
        }
    }

    val fragments by lazy { arrayListOf<Fragment>() }
    val tabs by lazy { arrayListOf<String>() }
    private val model: ProjectVM by viewModels {
        ProjectVMFactory
    }
    private val mAdapter by lazy {
        BaseBinderAdapter()
    }

    override fun initView() {
    }

    override fun initData() {
        binding?.model = model
        model.apply {
            getProjects()
            getObserveData(projectTopData) { data ->
                data as List<ProjectBean>
                fragments.clear()
                tabs.clear()
                data.forEach {
                    tabs.add(it.name)
                    fragments.add(ProjectArticleFragment.newInstance(it.id))
                }
                binding?.viewPage?.adapter =
                    ProjectTabAdapter(childFragmentManager, tabs, fragments)
                binding?.tab?.setViewPager(binding?.viewPage)
            }
        }
    }

}