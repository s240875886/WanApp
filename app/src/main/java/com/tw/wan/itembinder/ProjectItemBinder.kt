package com.tw.wan.itembinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.binder.QuickDataBindingItemBinder
import com.tw.wan.activity.WebActivity
import com.tw.wan.bean.Article
import com.tw.wan.databinding.ItemProjectBinding
import com.tw.wan.other.ItemClickPresenter

/**
 * @author thp
 * time 2020/5/8
 * desc:
 */
class ProjectItemBinder : QuickDataBindingItemBinder<Article, ItemProjectBinding>() {
    override fun convert(holder: BinderDataBindingHolder<ItemProjectBinding>, data: Article) {
        holder.dataBinding.run {
            item = data
            present = object : ItemClickPresenter {
                override fun onItemClick(v: View, item: Any) {
                    WebActivity.openWebAcitivty(context, data.link, data.title)
                }

            }
            executePendingBindings()
        }
    }

    override fun onCreateDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemProjectBinding {
        return ItemProjectBinding.inflate(layoutInflater, parent, false)
    }
}