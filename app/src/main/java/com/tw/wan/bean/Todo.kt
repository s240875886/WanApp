package com.tw.wan.bean

import java.io.Serializable

/**
 * @author thp
 * time 2020/6/18
 * desc:
 */
data class Todo(
    val curPage: Int = 0,
    val datas: List<TodoItem> = listOf(),
    val offset: Int = 0,
    val over: Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0
)


class TodoItem : Serializable {
    var completeDate: Long = 0
    val completeDateStr: String = ""
    var content: String = ""
    val date: Long = 0
    var dateStr: String = ""
    var id: Int = 0
    var priority: Int = 0
    var status: Int = 0
    var title: String = ""
    var type: Int = 0
    var userId: Int = 0
}
