package com.tw.wan.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tw.wan.R
import com.tw.wan.activity.WebActivity
import com.tw.wan.bean.BannerBean
import com.tw.wan.databinding.BannerImageTitleBinding
import com.tw.wan.other.ItemClickPresenter
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils


/**
 * @author thp
 * time 2020/4/27
 * desc:
 */
class ImageNetAdapter @JvmOverloads constructor(
    val context: Context? = null,
    datas: List<BannerBean>
) :
    BannerAdapter<BannerBean, ImageNetAdapter.ItemHolder>(datas) {
    class ItemHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val binding: BannerImageTitleBinding? = DataBindingUtil.bind(view)

    }

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(BannerUtils.getView(parent, R.layout.banner_image_title))

    }

    override fun onBindView(holder: ItemHolder, data: BannerBean, position: Int, size: Int) {
        holder.binding?.item = data
        holder.binding?.present = object : ItemClickPresenter {
            override fun onItemClick(v: View, item: Any) {
                context?.run {
                    WebActivity.openWebAcitivty(this, data.url, data.title)
                }

            }

        }
        holder.binding?.executePendingBindings()
    }
}