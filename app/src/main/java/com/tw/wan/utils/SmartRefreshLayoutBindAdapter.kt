package com.tw.wan.utils

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.tw.wan.R

/**
 * @author thp
 * time 2020/4/29
 * desc:
 */


/**
 *
 * 设置刷新的状态
 */
@BindingAdapter("refresh", requireAll = false)
fun SmartRefreshLayout.setSmartRefreshLayoutStatus(refresh: Int) {
//    refresh 0刷新中  1刷新完毕  2加载更多中  3加载完成 4 没有数据了
    setEnableAutoLoadMore(false)//使上拉加载具有弹性效果
    setEnableOverScrollDrag(false)//禁止越界拖动（1.0.4以上版本）
    setEnableOverScrollBounce(false)//关闭越界回弹功能
    setEnableAutoLoadMore(true)//开启自动加载更多
    when (refresh) {
        1->  finishRefresh()
        3 -> finishLoadMore()
        4 -> finishLoadMoreWithNoMoreData()
    }
}


/**
 * 反向绑定refresh的值
 */
@InverseBindingAdapter(attribute = "refresh", event = "refresh_change")
fun SmartRefreshLayout.getSmartRefreshLayoutStatus(): Int {
    return when (state) {
        RefreshState.Refreshing -> 0
        RefreshState.RefreshFinish -> 1
        RefreshState.Loading -> 2
        RefreshState.LoadFinish -> 3
        else -> -1
    }

}

/**
 * 事件监听
 */
@BindingAdapter("refresh_change", requireAll = false)
fun SmartRefreshLayout.setOnRefreshListener(inverseBindingListener: InverseBindingListener?) {
    inverseBindingListener?.run {
        setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                inverseBindingListener.onChange()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                inverseBindingListener.onChange()
            }

        })

    }

}

/**
 * 设置Material风格的头部
 */
@BindingAdapter("setMaterialHeader", requireAll = false)
fun SmartRefreshLayout.setMaterialHeader(isNeedHead: Boolean = false) {
    if (isNeedHead) {
        setRefreshHeader(
            MaterialHeader(this.context)
//                .setColorSchemeResources(R.color.colorRed, R.color.colorGreen, R.color.colorBlue)
        )
    }
}

/**
 * 设置经典头部
 */
@BindingAdapter("setClassicsHeader", requireAll = false)
fun SmartRefreshLayout.setClassicsHeader(isNeedHead: Boolean = false) {
    if (isNeedHead) {
        setRefreshHeader(
            ClassicsHeader(this.context)
                .setSpinnerStyle(SpinnerStyle.FixedBehind)

        )
    }
}

/**
 * 设置经典底部
 */
@BindingAdapter("setClassicsFoot", requireAll = false)
fun SmartRefreshLayout.setFootLayout(isNeedFoot: Boolean = false) {
    if (isNeedFoot) {
        setRefreshFooter(
            ClassicsFooter(this.context)
                .setSpinnerStyle(SpinnerStyle.FixedBehind)
                .setAccentColor(resources.getColor(R.color.color_A0A0A0))
        )
    }
}


