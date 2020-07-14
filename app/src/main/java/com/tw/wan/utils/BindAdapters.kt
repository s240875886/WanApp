package com.tw.wan.utils

import android.os.Build
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tw.wan.ext.loadImage
import com.youth.banner.util.BannerUtils

/**
 * @author thp
 * time 2020/4/24
 * desc:
 */
@BindingAdapter("banerImg")
fun ImageView.setImage(imgUrl: String) {
    //通过裁剪实现圆角
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        BannerUtils.setBannerRound(this, 20f)
    }
    loadImage(imgUrl)
}

@BindingAdapter("setImageUrl")
fun ImageView.setImageUrl(imgUrl: String) {
    loadImage(imgUrl)
}

@BindingAdapter("setSmoothMoveToPosition")
fun RecyclerView.setSmoothMoveToPosition(position: Int) {
    smoothScrollToPosition(position)
}
