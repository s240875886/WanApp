package com.tw.wan.itembinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.binder.QuickDataBindingItemBinder
import com.tw.wan.bean.NavBean
import com.tw.wan.databinding.ItemNavLeftBinding
import com.tw.wan.ext.scrollToTop
import com.tw.wan.other.ItemClickPresenter

/**
 * @author thp
 * time 2020/5/7
 * desc:
 */
class NavLeftItemBinder(private val recyclerViewRight: RecyclerView?) :
    QuickDataBindingItemBinder<NavBean, ItemNavLeftBinding>() {
    override fun convert(holder: BinderDataBindingHolder<ItemNavLeftBinding>, data: NavBean) {
        holder.dataBinding.item = data
        holder.dataBinding.present = object : ItemClickPresenter {
            override fun onItemClick(v: View, item: Any) {
                item as NavBean
                adapter.data.forEachIndexed { position, it ->
                    it as NavBean
                    if (item.cid == it.cid) {
                        it.isClick = true
                        //将recycleview 指定的position置顶
                        recyclerViewRight?.scrollToTop(position)
                    } else {
                        it.isClick = false
                    }
                    it.isClick = item.cid == it.cid
                }

                adapter.notifyDataSetChanged()


            }
        }
        holder.dataBinding.executePendingBindings()
    }

    override fun onCreateDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemNavLeftBinding {
        return ItemNavLeftBinding.inflate(layoutInflater, parent, false)
    }
}