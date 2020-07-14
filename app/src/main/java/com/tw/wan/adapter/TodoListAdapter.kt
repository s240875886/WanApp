package com.tw.wan.adapter

import com.chad.library.adapter.base.BaseNodeAdapter
import com.chad.library.adapter.base.entity.node.BaseNode
import com.tw.wan.bean.TodoItemNode
import com.tw.wan.bean.TodoNode
import com.tw.wan.data.viewmodels.TodoVM

/**
 * @author thp
 * time 2020/6/18
 * desc:
 */
class TodoListAdapter(todoVM: TodoVM, status: Int) : BaseNodeAdapter() {
    init {
        addFullSpanNodeProvider(ToDoTitleProvider(status))
        addNodeProvider(ToDoItemProvider(todoVM))
    }

    override fun getItemType(data: List<BaseNode>, position: Int): Int {
        return when (data[position]) {
            is TodoNode -> {
                0
            }
            is TodoItemNode -> {
                1
            }
            else -> -1
        }
    }

}