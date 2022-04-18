package com.holo.wanandroid.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.holo.architecture.base.activity.BaseActivity
import com.holo.architecture.ext.lifecycle.ActivityStack
import com.holo.architecture.ext.showErrToast
import com.holo.architecture.ext.showToast
import com.holo.architecture.logger.KLog
import com.holo.architecture.network.State
import com.holo.architecture.utils.flowbus.observeEvent
import com.holo.architecture.utils.netstatus.NetType
import com.holo.architecture.utils.netstatus.NetUtils
import com.holo.wanandroid.R
import com.holo.wanandroid.databinding.ActivityMainBinding
import com.holo.wanandroid.event.LoginEvent
import com.holo.wanandroid.event.LogoutEvent
import com.holo.wanandroid.ext.initMain
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun bottomPaddingView(): View = binding.navMenu

    override fun initView(savedInstanceState: Bundle?) {
        binding.vpMain.initMain(this)
        binding.navMenu.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.nav_home -> binding.vpMain.setCurrentItem(0, false)
                R.id.nav_project -> binding.vpMain.setCurrentItem(1, false)
                R.id.nav_discovery -> binding.vpMain.setCurrentItem(2, false)
                R.id.nav_public -> binding.vpMain.setCurrentItem(3, false)
                R.id.nav_mine -> binding.vpMain.setCurrentItem(4, false)
            }
        }
    }

    override fun observeViewModel() {
        observeEvent<LoginEvent> { event ->
            when (event.state) {
                is State.Loading -> showLoading()
                is State.Success -> {
                    hideLoading()
                    showToast("登录成功")
                }
                is State.Error -> {
                    hideLoading()
                    showErrToast(event.state.err)
                }
            }
        }
        observeEvent<LogoutEvent> { event ->
            when (event.state) {
                is State.Loading -> showLoading()
                is State.Success -> {
                    hideLoading()
                    showToast("退出登录")
                }
                is State.Error -> {
                    hideLoading()
                    showErrToast(event.state.err)
                }
            }
        }
    }

    override fun onNetworkStateChanged(netType: String) {
        // 网络状态改变
        KLog.e("测试", "Main网络状态改变：${netType}")
        //binding.netType = netType
        if (netType == NetType.WIFI) {
            if (NetUtils.is5GWifiConnected(this)) {
                KLog.e("测试", "这是5G WI-FI")
            } else {
                KLog.e("测试", "这是2.4G WI-FI")
            }
            KLog.e("测试", "WI-FI名：${NetUtils.getConnectedWifiSSID(this)}")
        }
    }

    private var exitTime = 0L
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast("再次点击退出" + getString(R.string.app_show_name))
                exitTime = System.currentTimeMillis()
            } else {
                ActivityStack.finishAllActivity()
                exitProcess(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}