package com.thp.library.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar
import com.lxj.statelayout.StateLayout
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.thp.library.net.RequestState

/**
 * @author thp
 * time 2020/4/14
 * desc:
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    private var dialog: LoadingPopupView? = null
    private var mRootView: View? = null

    //标题栏
    private var titleBar: TitleBar? = null

    //标题栏id 默认0 会自动查找布局中得TileBar组件得id
    open fun getTitleId(): Int = 0

    //视图id
    abstract fun getContentLayoutId(): Int

    //视图绑定
    protected var binding: T? = null

    //沉浸式
    private var immersionBar: ImmersionBar? = null

    //多视图
    var stateLayout: StateLayout? = null


    open fun getMultipleStatusViewId(): Int = 0

    open fun initView() {}
    open fun initData() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //绑定databinding
        binding = DataBindingUtil.inflate(inflater, getContentLayoutId(), container, false)
        mRootView = binding?.root
        //在Fragment中使用的时候需要注意下，要将StateLayout作为Fragment的View返回
        stateLayout = StateLayout(requireContext()).config(
            //点击errorView的回调
            retryAction = {
                onRetry()
            }
        ).wrap(mRootView)
        return stateLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.lifecycleOwner = this
        initImmersion()
        initView()
        initData()
    }


    open fun onRetry() {}

    /**
     * 是否需要启用沉浸式状态栏 默认启用
     */
    open fun isStatusBarEnabled(): Boolean {
        return true
    }

    /**
     * 根据资源 id 获取一个 View 对象
     */
    fun findViewById(@IdRes id: Int): View? = mRootView?.findViewById(id)

    /**
     *  初始化沉浸式状态栏
     */
    open fun initImmersion() {
        if (isStatusBarEnabled()) {
            //初始化配置
            statusBarConfig().init()
            var view: View? = null
            //设置了布局id
            if (getTitleId() > 0) {
                view = findViewById(getTitleId())
            }
            //默认在布局中查找TitleBar组件
            else if (getTitleId() == 0) {
                view = findTitleBar(mRootView as ViewGroup)
            }
            view?.apply {
                if (view is TitleBar) {
                    titleBar = view

                }
                //设置沉浸式
                ImmersionBar.setTitleBar(BaseActivity.mContext, this)

            }


        }
    }

    /**
     * 递归获取 ViewGroup 中得TileBar 对象
     */
    fun findTitleBar(group: ViewGroup): TitleBar? {
        for (i in 0 until group.childCount) {
            val view = group.getChildAt(i)
            if (view is TitleBar) {
                return view
            } else if (view is ViewGroup) {
                val titleBar = findTitleBar(view)
                if (titleBar != null) return titleBar
            }
        }
        return null
    }


    /**
     * 配置沉浸式
     */
    open fun statusBarConfig(): ImmersionBar {
        immersionBar =
            ImmersionBar.with(this)
                .statusBarDarkFont(statusBarDarkFont())
                .keyboardEnable(false)
        return immersionBar as ImmersionBar

    }

    /**
     * 获取状态栏字体颜色 默认黑色
     */
    open fun statusBarDarkFont(): Boolean {
        //false 白色字体
        return true
    }

    protected fun <T> getObserveData(
        liveData: LiveData<RequestState<T>>,
        dialogStyle: BaseDialogStyle = BaseDialogStyle.SateDialog,
        action: (T) -> Unit
    ) =
        liveData.observe(this, Observer
        { result ->
            if (result.isLoading()) {
                stateLayout?.showLoading()
            } else if (result.isSuccess()) {
                dimissLoadingDialog()
                if (result?.data != null) {
                    action(result.data)
                    stateLayout?.showContent()
                } else {
                    stateLayout?.showEmpty()
                }
            } else if (result.isError()) {
                dimissLoadingDialog()
                stateLayout?.showError()
            }
        })

    fun showLoadingDialog() {
        if (dialog == null) {
            dialog = XPopup.Builder(activity)
                .asLoading("正在加载中")
        }
        dialog?.show()
    }

    fun showLoadingDialog(context: Activity) {
        if (dialog == null) {
            dialog = XPopup.Builder(context)
                .asLoading("正在加载中")
        }
        dialog?.show()
    }

    fun dimissLoadingDialog() {
        dialog?.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        mRootView = null
        stateLayout = null
    }
}