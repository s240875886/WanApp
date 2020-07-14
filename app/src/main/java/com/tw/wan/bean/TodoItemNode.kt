package com.tw.wan.bean

import com.chad.library.adapter.base.entity.node.BaseNode

/**
 * @author thp
 * time 2020/6/18
 * desc:
 */
class TodoItemNode(val todoItem: TodoItem) : BaseNode() {
    override val childNode: MutableList<BaseNode>?
        get() = null

}