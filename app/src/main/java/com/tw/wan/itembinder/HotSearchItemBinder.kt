package com.tw.wan.itembinder

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.binder.QuickDataBindingItemBinder
import com.tw.wan.R
import com.tw.wan.activity.SearchListActivity
import com.tw.wan.data.database.bean.Word
import com.tw.wan.data.viewmodels.HotVM
import com.tw.wan.databinding.ItemHomeBinding
import com.tw.wan.databinding.ItemSearchHistoryBinding
import com.tw.wan.other.Constants
import com.tw.wan.other.ItemClickPresenter

/**
 * @author thp
 * time 2020/5/20
 * desc:
 */
class HotSearchItemBinder(val hotVM: HotVM) :
    QuickDataBindingItemBinder<Word, ItemSearchHistoryBinding>() {
    override fun convert(holder: BinderDataBindingHolder<ItemSearchHistoryBinding>, data: Word) {
        holder.dataBinding.run {
            item = data
            present = object : ItemClickPresenter {
                override fun onItemClick(v: View, item: Any) {
                    when (v.id) {
                        R.id.history_item -> {
                            var intent = Intent(context, SearchListActivity::class.java)
                            intent.putExtra(Constants.TITLE_NAME, data.word)
                            context.startActivity(intent)
                        }
                        R.id.history_delete -> {
                            hotVM.delete(data)
                        }
                    }
                }

            }
            executePendingBindings()
        }

    }

    override fun onCreateDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemSearchHistoryBinding {
        return ItemSearchHistoryBinding.inflate(layoutInflater, parent, false)
    }
}