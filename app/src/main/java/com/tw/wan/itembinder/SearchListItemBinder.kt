package com.tw.wan.itembinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.binder.QuickDataBindingItemBinder
import com.tw.wan.activity.WebActivity
import com.tw.wan.bean.Article
import com.tw.wan.databinding.ItemSearchListBinding
import com.tw.wan.databinding.ItemSystemListBinding
import com.tw.wan.other.ItemClickPresenter

/**
 * @author thp
 * time 2020/5/6
 * desc:
 */
class SearchListItemBinder : QuickDataBindingItemBinder<Article, ItemSearchListBinding>() {
    override fun convert(
        holder: BinderDataBindingHolder<ItemSearchListBinding>,
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
    ): ItemSearchListBinding {
        return ItemSearchListBinding.inflate(layoutInflater, parent, false)
    }
}