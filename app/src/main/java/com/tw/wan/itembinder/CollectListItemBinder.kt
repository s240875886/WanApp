package com.tw.wan.itembinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.binder.QuickDataBindingItemBinder
import com.tw.wan.activity.CollectActivity
import com.tw.wan.activity.WebActivity
import com.tw.wan.bean.Article
import com.tw.wan.data.viewmodels.CollectVM
import com.tw.wan.databinding.ItemCollectListBinding
import com.tw.wan.other.ItemClickPresenter

/**
 * @author thp
 * time 2020/6/5
 * desc:
 */
class CollectListItemBinder(val activity: CollectActivity, val collectVM: CollectVM) :
    QuickDataBindingItemBinder<Article, ItemCollectListBinding>() {
    init {
        collectVM.collectStatdus.observe(activity, Observer {
            collectVM.getCollectList()
        })
    }

    override fun convert(
        holder: BinderDataBindingHolder<ItemCollectListBinding>,
        data: Article
    ) {
        holder.dataBinding.item = data
        holder.dataBinding.model = collectVM
        holder.dataBinding.present = object : ItemClickPresenter {
            override fun onItemClick(v: View, item: Any) {
                if (item is String) {
                    WebActivity.openWebAcitivty(context, data.link, data.title)
                } else if (item is Boolean) {
                    collectVM.unCollect(data.originId)
                }
            }
        }
        holder.dataBinding.executePendingBindings()
    }

    override fun onViewAttachedToWindow(holder: BinderDataBindingHolder<ItemCollectListBinding>) {
        super.onViewAttachedToWindow(holder)

    }

    override fun onCreateDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemCollectListBinding {

        return ItemCollectListBinding.inflate(layoutInflater, parent, false)
    }
}


