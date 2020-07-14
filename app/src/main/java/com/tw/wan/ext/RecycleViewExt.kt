package com.tw.wan.ext

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * @author thp
 * time 2020/5/8
 * desc:
 */
/**
 * position 指定条目
 * 指定条目指定
 */
fun RecyclerView.scrollToTop(position: Int) {
    val mLinearLayoutManager = LinearLayoutManager(context)
    layoutManager = mLinearLayoutManager
    mLinearLayoutManager.scrollToPositionWithOffset(position, 0)
}
