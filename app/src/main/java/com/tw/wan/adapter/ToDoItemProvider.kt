package com.tw.wan.adapter

import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.tw.wan.R
import com.tw.wan.activity.AddTodoActivity
import com.tw.wan.bean.TodoItemNode
import com.tw.wan.data.viewmodels.TodoVM
import com.tw.wan.other.Constants


/**
 * @author thp
 * time 2020/6/18
 * desc:
 */
class ToDoItemProvider(val todoVM: TodoVM) : BaseNodeProvider() {
    private var pop: BasePopupView? = null
    override val itemViewType: Int = 1
    override val layoutId: Int = R.layout.item_todo_content

    init {
        addChildClickViewIds(R.id.todoDelete, R.id.todoFinish, R.id.content)
    }

    override fun convert(helper: BaseViewHolder, item: BaseNode) {
        item as TodoItemNode
        helper.setText(R.id.todoTitle, "标题:" + item.todoItem.title)
        helper.setText(R.id.todoContent, "内容:" + item.todoItem.content)
        helper.setText(R.id.todoTime, "时间:" + item.todoItem.dateStr)
        if (item.todoItem.status == 0) {
            helper.setImageResource(R.id.todoFinish, R.drawable.todo_check)
        } else {
            helper.setImageResource(R.id.todoFinish, R.drawable.todo_back)
        }
    }

    override fun onChildClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        data as TodoItemNode
        when (view.id) {
            //刪除清单
            R.id.todoDelete -> {
                if (pop == null) {
                    pop = XPopup.Builder(context).asConfirm(
                        "提示", "是否删除该条清单？"
                    ) {
                        todoVM.deleteTodo(data.todoItem.id)
                    }
                }
                pop?.let {
                    pop?.show()
                }
            }
            //内容
            R.id.content -> {
                context.startActivity(
                    Intent(context, AddTodoActivity::class.java).putExtra(
                        Constants.TODO_EDIT_DATA,
                        data.todoItem
                    )
                )
            }

            //完成清单
            R.id.todoFinish -> {
                if (data.todoItem.status == 0) {
                    todoVM.doneTodo(data.todoItem.id, 1)
                } else {
                    todoVM.doneTodo(data.todoItem.id, 0)
                }


            }
        }

    }
}