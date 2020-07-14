package com.tw.wan.bean

/**
 * @author thp
 * time 2020/5/5
 * desc:
 */

data class SystemBean(
    val children: List<Children> = listOf(),
    val courseId: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val order: Int = 0,
    val parentChapterId: Int = 0,
    val userControlSetTop: Boolean = false,
    val visible: Int = 0
)

data class Children(
    val children: List<Any> = listOf(),
    val courseId: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val order: Int = 0,
    val parentChapterId: Int = 0,
    val userControlSetTop: Boolean = false,
    val visible: Int = 0
)

