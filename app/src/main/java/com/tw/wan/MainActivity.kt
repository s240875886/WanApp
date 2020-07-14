package com.tw.wan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.thp.library.base.BaseActivity
import com.tw.wan.activity.*
import com.tw.wan.databinding.ActivityMainBinding
import com.tw.wan.event.MainEvent
import com.tw.wan.fragment.*
import com.tw.wan.other.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_drawer_header.view.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val fragments by lazy {
        SparseArray<Fragment>()
    }
    var fragmentTag = ""

    // 当前显示的 fragment
    private var mCurrentFragment: Fragment? = null
    private var mLastFragment: Fragment? = null
    var index: Int = 0
    private var mLastIndex: Int = -1
    private lateinit var headView: View
    override fun getContentLayoutId(): Int = R.layout.activity_main
    override fun getTitleId(): Int = R.id.toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //第一次启动
        if (savedInstanceState == null) {
            switchFragment(Constants.HOME)
        }
        initToolBar()
        initBottomNavigation()
        initDrawerLayout()
    }

    private fun initDrawerLayout() {
        headView = navigation_draw.getHeaderView(0)
        headView.me_name.text = SPUtils.getInstance().getString(Constants.NICK_NAME)
        headView.me_image.setOnClickListener {
            if (!Constants.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        navigation_draw.setNavigationItemSelectedListener {
            when (it.itemId) {
                //排行榜
                R.id.nav_menu_rank -> {
                    if (Constants.isLogin) {
                        startActivity(Intent(this, RankActivity::class.java))
                    }else{
                        ToastUtils.showShort(getString(R.string.login_tip))
                    }

                }
                //广场
                R.id.nav_menu_square -> {
                }
                //收藏
                R.id.nav_menu_collect -> {
                    if (Constants.isLogin) {
                        startActivity(Intent(this, CollectActivity::class.java))
                    }else{
                        ToastUtils.showShort(getString(R.string.login_tip))
                    }
                }
                //我的分享
                R.id.nav_menu_share -> {
                }
                //每日一问
                R.id.nav_menu_question -> {
                }
                //待办清单
                R.id.nav_menu_todo -> {
                    if (Constants.isLogin) {
                        startActivity(Intent(this, TodoActivity::class.java))
                    }else{
                        ToastUtils.showShort(getString(R.string.login_tip))
                    }


                }
                //设置
                R.id.nav_menu_setting -> {

                }
                R.id.nav_menu_logout -> {
                    SPUtils.getInstance().remove(Constants.COOKIE_KEY)
                    SPUtils.getInstance().remove(Constants.NICK_NAME)
                    SPUtils.getInstance().remove(Constants.USER_NAME)
                    Constants.isLogin = false;
                    headView.me_name.text = SPUtils.getInstance().getString(Constants.NICK_NAME)
                }
            }

            // 关闭侧边栏
            drawer_main.closeDrawers()
            true
        }
    }

    private fun initBottomNavigation() {
        bottom_ngs.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    setToolBarTitle(toolbar, getString(R.string.navigation_home))
                    switchFragment(Constants.HOME)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_wechat -> {
                    setToolBarTitle(toolbar, getString(R.string.navigation_wechat))
                    switchFragment(Constants.WX)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_system -> {
                    setToolBarTitle(toolbar, getString(R.string.navigation_system))
                    switchFragment(Constants.SYSTEM)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_navigation -> {
                    setToolBarTitle(toolbar, getString(R.string.navigation_navigation))
                    switchFragment(Constants.NAVITION)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_project -> {
                    setToolBarTitle(toolbar, getString(R.string.navigation_project))
                    switchFragment(Constants.PROJECT)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun initToolBar() {
        //设置标题
        setToolBarTitle(toolbar, getString(R.string.navigation_home))
        //设置导航图标、按钮有旋转特效
        val toggle = ActionBarDrawerToggle(
            this, drawer_main, toolbar, R.string.app_name, R.string.app_name
        )
        drawer_main.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 设置toolBar标题
     */
    private fun setToolBarTitle(toolbar: Toolbar, title: String) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // recreate时保存当前页面位置
        outState.putInt("index", mLastIndex)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // 恢复recreate前的页面
        mLastIndex = savedInstanceState.getInt("index")
        switchFragment(mLastIndex)
    }

    private fun switchFragment(index: Int) {
        val fragmentManager = supportFragmentManager
        val transaction =
            fragmentManager.beginTransaction()
        // 将当前显示的fragment和上一个需要隐藏的fragment分别加上tag, 并获取出来
        // 给fragment添加tag,这样可以通过findFragmentByTag找到存在的fragment，不会出现重复添加
        mCurrentFragment = fragmentManager.findFragmentByTag(index.toString())
        mLastFragment = fragmentManager.findFragmentByTag(mLastIndex.toString())
        // 如果位置不同
        if (index != mLastIndex) {
            if (mLastFragment != null) {
                transaction.hide(mLastFragment!!)
            }
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.content, mCurrentFragment!!, index.toString())
            } else {
                transaction.show(mCurrentFragment!!)
            }
        }

        // 如果位置相同或者新启动的应用
        if (index == mLastIndex) {
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.content, mCurrentFragment!!, index.toString())
            }
        }
        transaction.commit()
        mLastIndex = index
    }


    private fun getFragment(index: Int): Fragment {
        var fragment: Fragment? = fragments.get(index)
        if (fragment == null) {
            when (index) {
                Constants.HOME -> fragment = HomeFragment.getInstance()
                Constants.SYSTEM -> fragment = SystemFragment.getInstance()
                Constants.NAVITION -> fragment = NavFragment.getInstance()
                Constants.WX -> fragment = WxFragment.getInstance()
                Constants.PROJECT -> fragment = ProjectFragment.getInstance()
            }
            fragments.put(index, fragment)
        }
        return fragment!!
    }

    @SuppressLint("WrongConstant")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            //将滑动菜单显示出来
            android.R.id.home -> {
                drawer_main.openDrawer(Gravity.START)
                return true
            }
            R.id.action_search -> {
                val intent = Intent(this, HotActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MainEvent) {
        headView.me_name.text = SPUtils.getInstance().getString(Constants.NICK_NAME)
    }
}

