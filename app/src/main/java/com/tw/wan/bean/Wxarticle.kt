package com.tw.wan.bean

/**
 * @author thp
 * time 2020/5/12
 * desc:
 */
data class Wxarticle(
    val children: List<Any> = listOf(),
    val courseId: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val order: Int = 0,
    val parentChapterId: Int = 0,
    val userControlSetTop: Boolean = false,
    val visible: Int = 0
)

