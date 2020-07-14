package com.tw.wan.bean

/**
 * @author thp
 * time 2020/5/6
 * desc:
 */

data class SystemDetail(
    val curPage: Int = 0,
    val datas: List<Article> = listOf(),
    val offset: Int = 0,
    val over: Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0
)

