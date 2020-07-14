package com.tw.wan.lifecycle

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.tw.wan.adapter.ImageNetAdapter
import com.tw.wan.bean.BannerBean
import com.youth.banner.Banner


/**
 * @author thp
 * time 2020/4/24
 * desc:
 */
class BannerLifeCycle @JvmOverloads constructor(
    context: Context, attrs: AttributeSet, defStyleAttr: Int = 0
) : Banner<BannerBean, ImageNetAdapter>(context, attrs, defStyleAttr),
    LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        //开始轮播
        start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        //结束轮播
        stop()
    }
}