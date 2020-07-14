package com.tw.wan.itembinder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.binder.QuickDataBindingItemBinder
import com.tw.wan.activity.WebActivity
import com.tw.wan.bean.Article
import com.tw.wan.bean.NavBean
import com.tw.wan.databinding.ItemNavRightBinding

/**
 * @author thp
 * time 2020/5/7
 * desc:
 */
class NavRightItemBinder : QuickDataBindingItemBinder<NavBean, ItemNavRightBinding>() {
    override fun convert(holder: BinderDataBindingHolder<ItemNavRightBinding>, data: NavBean) {
        holder.dataBinding.item = data
        holder.dataBinding.labels.setOnLabelClickListener { _, data, _ ->
            data as Article
            WebActivity.openWebAcitivty(context, data.link,data.title)
        }
        holder.dataBinding.executePendingBindings()

    }

    override fun onCreateDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemNavRightBinding {
        return ItemNavRightBinding.inflate(layoutInflater, parent, false)
    }
}