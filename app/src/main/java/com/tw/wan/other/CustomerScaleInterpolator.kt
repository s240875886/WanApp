package com.tw.wan.other

import android.view.animation.Interpolator

/**
 * @author thp
 * time 2020/5/6
 * desc:
 */
class CustomerScaleInterpolator() : Interpolator {
    override fun getInterpolation(input: Float): Float {
        return when {
            input < 0.3535 -> bounce(input)
            input < 0.7408 -> bounce(input - 0.54719f) + 0.7f
            input < 0.9644 -> bounce(input - 0.8526f) + 0.9f
            else -> bounce(input - 1.0435f) + 0.95f
        }
    }

    fun bounce(t: Float): Float = t * t * 8;

}