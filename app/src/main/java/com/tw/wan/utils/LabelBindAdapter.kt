package com.tw.wan.utils

import androidx.databinding.BindingAdapter
import com.donkingliang.labels.LabelsView
import com.tw.wan.bean.Article
import com.tw.wan.bean.Children
import com.tw.wan.bean.HotBean
import java.util.*


/**
 * @author thp
 * time 2020/5/5
 * desc:
 */
@BindingAdapter("setDatas")
fun LabelsView.setSystemDatas(datas: List<Children>) {


    setLabels(
        datas
    ) { label, position, data ->
        val myRandom = Random()
        val ranColor = -0x1000000 or myRandom.nextInt(0x00ffffff)
        label.setTextColor(ColorUtil.randomColor())
        return@setLabels data.name
    }

}

@BindingAdapter("setDatas")
fun LabelsView.setNavDatas(datas: List<Article>) {
    setLabels(
        datas
    ) { label, position, data ->
        val myRandom = Random()
        val ranColor = -0x1000000 or myRandom.nextInt(0x00ffffff)
        label.setTextColor(ColorUtil.randomColor())
        return@setLabels data.title
    }

}

@BindingAdapter("setDatas")
fun LabelsView.setHotDatas(datas: List<HotBean>) {
    setLabels(
        datas
    ) { label, position, data ->
        val myRandom = Random()
        val ranColor = -0x1000000 or myRandom.nextInt(0x00ffffff)
        label.setTextColor(ColorUtil.randomColor())
        return@setLabels data.name
    }

}