package com.thp.library.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.lxj.statelayout.StateLayout
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.lxj.xpopup.interfaces.SimpleCallback
import com.thp.library.net.RequestState
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.properties.Delegates

/**
 * @author thp
 * time 2020/4/13
 * desc:
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    companion object {
        var mContext: Activity? = null
    }

    private var dialog: LoadingPopupView? = null

    //标题栏
    private var titleBar: TitleBar? = null

    //标题栏id 默认0 会自动查找布局中得TileBar组件得id
    open fun getTitleId(): Int = 0

    //视图id
    protected abstract fun getContentLayoutId(): Int

    //视图绑定
    protected var binding by Delegates.notNull<T>()

    //多视图
    var stateLayout: StateLayout? = null

    //沉浸式
    private var immersionBar: ImmersionBar? = null


    open fun getMultipleStatusViewId(): Int = 0
    open fun initView() {}
    open fun initData() {}
    open fun initListener() {}
    open fun onRightClicck() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getContentLayoutId())
        binding.lifecycleOwner = this
        mContext = this
        stateLayout = StateLayout(this).config(
            retryAction = { onRetry() }
        ).wrap(this)
        initImmersion()
        initView()
        initData()
        initListener()
        EventBus.getDefault().register(this)
    }


    open fun onRetry() {}

    /**
     * 是否需要启用沉浸式状态栏 默认启用
     */
    open fun isStatusBarEnabled(): Boolean {
        return true
    }

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
                view = findTitleBar(window.decorView as ViewGroup)
            }
            view?.apply {
                if (view is TitleBar) {
                    titleBar = view
                    titleBar?.setOnTitleBarListener(object : OnTitleBarListener {
                        override fun onLeftClick(v: View?) {
                            finish()
                        }

                        override fun onRightClick(v: View?) {
                            onRightClick(v)
                        }

                        override fun onTitleClick(v: View?) {
                        }

                    })
                }
                //设置沉浸式
                ImmersionBar.setTitleBar(mContext, this)

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
        refreshStat: MutableLiveData<Int> = MutableLiveData<Int>().apply {
            value = -1
        },
        success: (T) -> Unit
    ) =
        liveData.observe(this, Observer { result ->
            if (result.isLoading() && refreshStat.value == -1) {
                showLoadingDialog()
            } else if (result.isSuccess()) {
                dimissLoadingDialog()
                if (result?.data != null) {
                    success(result.data)
                    stateLayout?.showContent()
                } else {
                    stateLayout?.showEmpty()
                }
            } else if (result.isError()) {
                dimissLoadingDialog()
                stateLayout?.showError()
            }
        })

    override fun onDestroy() {
        super.onDestroy()
        mContext = null
        titleBar = null
        dialog = null
        EventBus.getDefault().unregister(this)
    }


    fun showLoadingDialog() {
        showLoadingDialog(this)
    }

    fun showLoadingDialog(context: Activity) {
        if (dialog == null) {
            dialog = XPopup.Builder(context)
                .dismissOnTouchOutside(false)
                //设置显示和隐藏的回调
                .setPopupCallback(object : SimpleCallback() {
                    override fun onBackPressed(): Boolean {
                        dimissLoadingDialog()
                        return super.onBackPressed()
                    }
                })
                .asLoading("正在加载中")

        }
        dialog?.show()
    }

    fun dimissLoadingDialog() {
        dialog?.dismiss()
    }

    //没有作用 防止报错
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
    }
}
