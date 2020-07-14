package com.tw.wan.bean

/**
 * @author thp
 * time 2020/6/16
 * desc:
 */
data class Rank(
    val curPage: Int = 0,
    val datas: List<RankItem> = listOf(),
    val offset: Int = 0,
    val over: Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0
)

data class RankItem(
    val coinCount: Int = 0,
    val level: Int = 0,
    val rank: String = "",
    val userId: Int = 0,
    val username: String = ""

)