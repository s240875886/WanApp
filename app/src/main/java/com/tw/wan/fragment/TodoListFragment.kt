package com.tw.wan.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.entity.node.BaseNode
import com.thp.library.base.BaseFragment
import com.tw.wan.R
import com.tw.wan.adapter.TodoListAdapter
import com.tw.wan.bean.TodoItem
import com.tw.wan.bean.TodoItemNode
import com.tw.wan.bean.TodoNode
import com.tw.wan.data.viewmodels.TodoVM
import com.tw.wan.data.viewmodels.TodoVMFactory
import com.tw.wan.databinding.FragmentTodoListBinding
import com.tw.wan.event.TodoDone
import com.tw.wan.other.Constants
import org.greenrobot.eventbus.EventBus

/**
 * @author thp
 * time 2020/5/8
 * desc:
 */
class TodoListFragment : BaseFragment<FragmentTodoListBinding>() {
    companion object {
        fun newInstance(status: Int): Fragment {
            val bundle = Bundle()
            bundle.putInt("status", status)
            val fragment = TodoListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val status: Int by lazy { arguments?.getInt("status") ?: 0 }
    override fun getContentLayoutId(): Int = R.layout.fragment_todo_list
    private val model: TodoVM by viewModels {
        TodoVMFactory
    }
    private val mAdapter by lazy {
        TodoListAdapter(model, status)
    }

    override fun initView() {
        binding?.rvList?.adapter = mAdapter
    }

    override fun initData() {
        model.apply {
            binding?.model = this
            getObserveData(loadStatus) {
                if (it is Int) {
                    when (it) {
                        Constants.TODO_DEL -> {
                            loadTodoData(status)
                        }
                        Constants.TODO_DONE -> {
                            EventBus.getDefault().post(TodoDone().apply { status = 1 })
                        }
                        Constants.TODO_BACK -> {
                            EventBus.getDefault().post(TodoDone().apply { status = 0 })
                        }
                    }
                } else {
                    it as List<TodoItem>
                    mAdapter.setList(transFormData(it))
                }
            }
            refreshStatus.observe(this@TodoListFragment, Observer {
                refreshOrLoadMore(it)
            })

        }

    }

    private fun transFormData(list: List<TodoItem>): MutableList<BaseNode> {
        //存储时间数组
        var list2 = mutableListOf<String>()
        //根据时间去重
        list.forEach { item ->
            if (!list2.contains(item.dateStr)) {
                list2.add(item.dateStr)
            }
        }

        var mylist = mutableListOf<BaseNode>()
        list2.forEach() { dateStr ->
            var datas = list.filter { item2 ->
                dateStr == item2.dateStr
            }
            var items = mutableListOf<BaseNode>()
            datas.forEach { item ->
                var item = TodoItemNode(item)
                items.add(item)
            }
            var firstItem = TodoNode(items, dateStr)
            mylist.add(firstItem)
        }
        return mylist
    }

    override fun onResume() {
        super.onResume()
        model.loadTodoData(status)
    }

}