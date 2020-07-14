package com.tw.wan.adapter

import android.view.View
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tw.wan.R
import com.tw.wan.bean.TodoNode

/**
 * @author thp
 * time 2020/6/18
 * desc:
 */
class ToDoTitleProvider(val status: Int) : BaseNodeProvider() {
    override val itemViewType: Int = 0
    override val layoutId: Int = R.layout.item_todo_title

    override fun convert(helper: BaseViewHolder, item: BaseNode) {
        item as TodoNode
        if (status == 1) {
            helper.setTextColorRes(R.id.todoTime, R.color.color_E5A340)
            helper.setBackgroundResource(R.id.todoTime, R.color.color_FFFBF4)
        } else {
            helper.setTextColorRes(R.id.todoTime, R.color.color_2AB27F)
            helper.setBackgroundResource(R.id.todoTime, R.color.color_CEF3EA)
        }
        helper.setText(R.id.todoTime, item.title)
        if (item.isExpanded) {
            helper.setImageResource(R.id.imgArrow, R.drawable.arrow_drop_up)
        } else {
            helper.setImageResource(R.id.imgArrow, R.drawable.arrow_drop_down)
        }
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        data as TodoNode
        if (data.isExpanded) {
            helper.setImageResource(R.id.imgArrow, R.drawable.arrow_drop_down)
        } else {
            helper.setImageResource(R.id.imgArrow, R.drawable.arrow_drop_up)
        }
        getAdapter()?.expandOrCollapse(position)
    }
}