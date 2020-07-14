package com.tw.wan.itembinder

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.binder.QuickDataBindingItemBinder
import com.tw.wan.activity.SystemListActivity
import com.tw.wan.bean.Children
import com.tw.wan.bean.SystemBean
import com.tw.wan.databinding.ItemSystemBinding
import com.tw.wan.other.Constants
import com.tw.wan.other.CustomerScaleInterpolator

/**
 * @author thp
 * time 2020/5/5
 * desc:
 */
class SystemItemBinder : QuickDataBindingItemBinder<SystemBean, ItemSystemBinding>() {
    override fun convert(holder: BinderDataBindingHolder<ItemSystemBinding>, data: SystemBean) {
        holder.dataBinding.item = data

        holder.dataBinding.labels.setOnLabelClickListener { _, data, _ ->
            data as Children
            var intent = Intent(context, SystemListActivity::class.java)
            intent.putExtra(Constants.CID, data.id)
            intent.putExtra(Constants.TITLE_NAME, data.name)
            context.startActivity(intent)
        }
        holder.dataBinding.executePendingBindings()

    }

    override fun onCreateDataBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemSystemBinding {
        return ItemSystemBinding.inflate(layoutInflater, parent, false)
    }

    override fun onViewAttachedToWindow(holder: BinderDataBindingHolder<ItemSystemBinding>) {
        super.onViewAttachedToWindow(holder)
        val animatorX =
            ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.0f, 1.0f)
        val animatorY =
            ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.0f, 1.0f)
        val set = AnimatorSet()
        set.duration = 1000
        //它的值越大，它回弹效果越慢
        set.interpolator = CustomerScaleInterpolator()
        set.playTogether(animatorX, animatorY)
        set.start()
    }

    override fun onViewDetachedFromWindow(holder: BinderDataBindingHolder<ItemSystemBinding>) {
        super.onViewDetachedFromWindow(holder)
        val animatorX =
            ObjectAnimator.ofFloat(holder.itemView, "scaleX", 1.0f, 0.0f)
        val animatorY =
            ObjectAnimator.ofFloat(holder.itemView, "scaleY", 1.0f, 0.0f)
        val set = AnimatorSet()
        set.duration = 1000
        //它的值越大，它回弹效果越慢
        set.interpolator = CustomerScaleInterpolator()
        set.playTogether(animatorX, animatorY)
        set.start()
    }
}