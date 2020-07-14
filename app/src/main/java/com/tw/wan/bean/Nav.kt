package com.tw.wan.bean

/**
 * @author thp
 * time 2020/5/7
 * desc:
 */

data class NavBean(
    val articles: List<Article> = listOf(),
    val cid: Int = 0,
    val name: String = "",
    var isClick: Boolean = false
)

