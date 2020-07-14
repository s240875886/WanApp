package com.tw.wan.itembinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.binder.QuickDataBindingItemBinder
import com.tw.wan.activity.WebActivity
import com.tw.wan.bean.Article
import com.tw.wan.databinding.ItemSystemListBinding
import com.tw.wan.other.ItemClickPresenter

/**
 * @author thp
 * time 2020/5/6
 * desc:
 */
class SystemListItemBinder : QuickDataBindingItemBinder<Article, ItemSystemListBinding>() {
    override fun convert(
        holder: BinderDataBindingHolder<ItemSystemListBinding>,
        data: Article
    ) {
        holder.dataBinding.item = data
        holder.dataBinding.present = object : ItemClickPresenter {
            override fun onItemClick(v: View, item: Any) {
                WebActivity.openWebAcitivty(context, data.link, data.title)
            }

        }

        holder.dataBinding.executePendingBindings()
    }

    override fun onCreateDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemSystemListBinding {
        return ItemSystemListBinding.inflate(layoutInflater, parent, false)
    }
}