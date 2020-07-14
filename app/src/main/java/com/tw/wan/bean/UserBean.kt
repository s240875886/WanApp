package com.tw.wan.bean

/**
 * @author thp
 * time 2020/6/1
 * desc:
 */
data class UserBean(
    val admin: Boolean = false,
    val chapterTops: List<Any> = listOf(),
    val collectIds: List<Any> = listOf(),
    val email: String = "",
    val icon: String = "",
    val id: Int = 0,
    val nickname: String = "",
    val password: String = "",
    val publicName: String = "",
    val token: String = "",
    val type: Int = 0,
    val username: String = ""
)
