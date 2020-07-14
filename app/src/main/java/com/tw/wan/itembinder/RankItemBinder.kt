package com.tw.wan.itembinder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.binder.QuickDataBindingItemBinder
import com.tw.wan.bean.RankItem
import com.tw.wan.databinding.ItemRankBinding

/**
 * @author thp
 * time 2020/6/16
 * desc:
 */
class RankItemBinder : QuickDataBindingItemBinder<RankItem, ItemRankBinding>() {
    override fun convert(holder: BinderDataBindingHolder<ItemRankBinding>, data: RankItem) {
        holder.dataBinding.run {
            item = data
            executePendingBindings()
        }
    }

    override fun onCreateDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemRankBinding {
        return ItemRankBinding.inflate(layoutInflater, parent, false)
    }
}