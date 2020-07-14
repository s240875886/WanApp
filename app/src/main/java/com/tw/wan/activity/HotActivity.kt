package com.tw.wan.activity

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseBinderAdapter
import com.thp.library.base.BaseActivity
import com.tw.wan.R
import com.tw.wan.bean.HotBean
import com.tw.wan.data.database.bean.Word
import com.tw.wan.data.viewmodels.HotVM
import com.tw.wan.data.viewmodels.HotVMFactory
import com.tw.wan.databinding.ActivityHotBinding
import com.tw.wan.itembinder.HotSearchItemBinder
import com.tw.wan.other.Constants
import kotlinx.android.synthetic.main.customer_search_bar.*

class HotActivity : BaseActivity<ActivityHotBinding>() {
    override fun getContentLayoutId(): Int = R.layout.activity_hot
    override fun getTitleId(): Int = R.id.search_bar
    private val model: HotVM by viewModels {
        HotVMFactory
    }
    private val mAdapter by lazy {
        BaseBinderAdapter()
    }

    override fun initData() {
        binding.searchList.adapter = mAdapter
        mAdapter.run {
            addItemBinder(Word::class.java, HotSearchItemBinder(model))
            val view = LayoutInflater.from(this@HotActivity)
                .inflate(R.layout.history_foot, binding.searchList, false)
            addFooterView(view)
            footerLayout?.setOnClickListener {
                model.deleteAll()
            }
        }


        binding.model = model
        binding.list = listOf()
        model.run {
            getHotData()
            getObserveData(loadStatus) {
                var datas = it as List<HotBean>
                binding.list = datas
            }
            getAllWords()
            historySearchData.observe(this@HotActivity, Observer {
                if (it == null || it.isEmpty()) {
                    mAdapter.footerLayout?.visibility = View.GONE
                } else {
                    mAdapter.footerLayout?.visibility = View.VISIBLE
                }

                mAdapter.setList(it)
            })

        }
        binding.labels.setOnLabelClickListener { _, data, _ ->
            data as HotBean
            openSearchActiviy(data.name)
        }
        initListener()

    }

    fun openSearchActiviy(key: String) {
        model.insert(Word(key))
        model.getAllWords()
        var intent = Intent(this, SearchListActivity::class.java)
        intent.putExtra(Constants.TITLE_NAME, key)
        startActivity(intent)
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            finish()
        }
        et_content.setOnEditorActionListener(TextView.OnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (et_content.text.isNullOrEmpty()) {
                    ToastUtils.showShort(getString(R.string.hint_keyword))
                    return@OnEditorActionListener false
                }
                openSearchActiviy(et_content.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
        iv_close.setOnClickListener {
            et_content.setText("")
        }
        tv_search.setOnClickListener {
            if (et_content.text.isNullOrEmpty()) {
                ToastUtils.showShort(getString(R.string.hint_keyword))
                return@setOnClickListener
            }
            openSearchActiviy(et_content.text.toString())
        }
    }
}
