package com.tw.wan.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


/**
 * @author thp
 * time 2020/5/5
 * desc:
 */
/**
 * 加载图片
 */
fun ImageView.loadImage(
    path: String,
    placeholder: Int = 0,
    errorResId: Int = 0,
    useCache: Boolean = true
) {

    Glide.with(this).load(path).placeholder(placeholder)
        .error(errorResId)
        .skipMemoryCache(useCache)
        .into(this)
}

/**
 * 加载圆形图片
 */
fun ImageView.loadCircleImage(
    path: String,
    placeholder: Int = 0,
    errorResId: Int = 0,
    useCache: Boolean = true
) {
    Glide.with(this).load(path).placeholder(placeholder)
        .error(errorResId)
        .circleCrop()
        .skipMemoryCache(useCache)
        .into(this)
}

/**
 * 加载圆角图片
 */
fun ImageView.loadRoundCornerImage(
    path: String,
    roundingRadius: Int = 6,
    placeholder: Int = 0,
    errorResId: Int = 0,

    useCache: Boolean = true
) {
    //设置图片圆角角度
    val roundedCorners = RoundedCorners(roundingRadius)
    var options = RequestOptions.bitmapTransform(roundedCorners)
    Glide.with(this).load(path).placeholder(placeholder)
        .error(errorResId)
        .circleCrop()
        .apply(options)
        .skipMemoryCache(useCache)
        .into(this)
}