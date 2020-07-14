package com.tw.wan.itembinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.chad.library.adapter.base.binder.QuickDataBindingItemBinder
import com.tw.wan.activity.WebActivity
import com.tw.wan.bean.Article
import com.tw.wan.data.viewmodels.CollectVM
import com.tw.wan.databinding.ItemHomeBinding
import com.tw.wan.other.ItemClickPresenter

/**
 * @author thp
 * time 2020/4/27
 * desc:
 */
class HomeItemBinder(val activity: FragmentActivity, val collectVM: CollectVM) :
    QuickDataBindingItemBinder<Article, ItemHomeBinding>() {
    override fun convert(holder: BinderDataBindingHolder<ItemHomeBinding>, data: Article) {
        holder.dataBinding.item = data
        holder.dataBinding.present = object : ItemClickPresenter {
            override fun onItemClick(v: View, item: Any) {
                //点击item
                if (item is String) {
                    WebActivity.openWebAcitivty(context, data.link, data.title)
                }
                //点击收藏图标
                else if (item is Boolean) {
                    if (!data.collect) {
                        collectVM.atCollect(data.id)
                    } else {
                        collectVM.unCollect(data.id)
                    }
                    data.collect = !data.collect
                    adapter.notifyItemChanged(adapter.getItemPosition(data) + 1)
                }

            }
        }
        holder.dataBinding.executePendingBindings()
    }

    override fun onCreateDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemHomeBinding {
        return ItemHomeBinding.inflate(layoutInflater, parent, false)
    }

}