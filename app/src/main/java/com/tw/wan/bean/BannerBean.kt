package com.tw.wan.bean

/**
 * @author thp
 * time 2020/4/24
 * desc:
 */
data class BannerBean(
    val desc: String = "",
    val id: Int = 0,
    val imagePath: String = "",
    val isVisible: Int = 0,
    val order: Int = 0,
    val title: String = "",
    val type: Int = 0,
    val url: String = ""
)