package com.tw.wan.utils

import android.graphics.Color
import java.util.*

/**
 * @author thp
 * time 2020/5/6
 * desc:
 */
class ColorUtil {
    companion object {
        fun randomColor(): Int {
            val random = Random()
            //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
            val red = random.nextInt(150)
            //0-190
            val green = random.nextInt(150)
            //0-190
            val blue = random.nextInt(150)
            //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
            return Color.rgb(red, green, blue)
        }
    }
}