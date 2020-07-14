package com.thp.library.utils

import android.view.View
import com.thp.library.utils.ViewClickDelay.hash
import com.thp.library.utils.ViewClickDelay.lastClickTime

/**
 * @author thp
 * time 2020/6/23
 * desc:
 */
object ViewClickDelay {
    var hash: Int = 0
    var lastClickTime: Long = 0
}

fun View.clickDelay(delay: Long = 600L, clickAction: (View) -> Unit) {
    this.setOnClickListener {
        if (this.hashCode() != hash) {
            hash = this.hashCode()
            lastClickTime = System.currentTimeMillis()
            clickAction(this)
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > delay) {
                lastClickTime = System.currentTimeMillis()
                clickAction(this)
            }
        }
    }
}