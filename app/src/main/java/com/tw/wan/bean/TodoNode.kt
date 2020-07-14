package com.tw.wan.bean

import com.chad.library.adapter.base.entity.node.BaseExpandNode
import com.chad.library.adapter.base.entity.node.BaseNode

/**
 * @author thp
 * time 2020/6/18
 * desc:
 */
class TodoNode(override val childNode: MutableList<BaseNode>?, val title: String) : BaseExpandNode() {

}