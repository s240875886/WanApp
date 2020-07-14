package com.tw.wan.activity

import androidx.activity.viewModels
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.TimeUtils
import com.thp.library.base.BaseActivity
import com.tw.wan.R
import com.thp.library.utils.clickDelay
import com.tw.wan.bean.TodoItem
import com.tw.wan.data.viewmodels.TodoVM
import com.tw.wan.data.viewmodels.TodoVMFactory
import com.tw.wan.databinding.ActivityAddTodoBinding
import com.tw.wan.other.Constants


class AddTodoActivity : BaseActivity<ActivityAddTodoBinding>() {
    private var isEdit: Boolean = false
    private var pvTime: TimePickerView? = null

    override fun getContentLayoutId(): Int = R.layout.activity_add_todo
    private val model: TodoVM by viewModels {
        TodoVMFactory
    }

    override fun initListener() {
        binding.content.tvTimeSelect.setOnClickListener {
            //隐藏软键盘
            KeyboardUtils.hideSoftInput(this)
            chooseTime()
        }
        binding.content.btSave.clickDelay(1000) {
            if (isEdit) {
                model.updateTodo((model.item.value?.id)!!.toInt())
            } else {
                model.addTodo()
            }
        }
    }

    override fun initData() {
        //编辑的情况 设置数据
        intent.getSerializableExtra(Constants.TODO_EDIT_DATA)?.let {
            isEdit = true
            binding.titleBar.title = getString(R.string.todo_edit)
            model.item.postValue(it as TodoItem)
        }
        binding.model = model
        getObserveData(model.loadStatus) {
            finish()
        }
    }

    //时间选择器
    fun chooseTime() {
        if (pvTime == null) {
            pvTime =
                TimePickerBuilder(this@AddTodoActivity,
                    OnTimeSelectListener { date, v ->
                        binding.content.tvTimeSelect.text =
                            TimeUtils.date2String(date, "yyyy-MM-dd")
                    }).build()
        }
        pvTime?.show()
    }
}