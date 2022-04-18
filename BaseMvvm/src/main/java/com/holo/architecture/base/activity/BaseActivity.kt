package com.holo.architecture.base.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.navigationBarHeight
import com.holo.architecture.R
import com.holo.architecture.ext.showErrToast
import com.holo.architecture.logger.KLog
import com.holo.architecture.network.ListState
import com.holo.architecture.network.NetworkStateManager
import com.holo.architecture.network.State
import com.holo.architecture.utils.ViewBindingUtil
import com.holo.architecture.utils.netstatus.NetType
import com.holo.architecture.widget.TransBehavior
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @Desc Activity基类
 * @Author holo
 * @Date 2022/1/7
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB
    private var loadingDialog: MaterialDialog? = null
    private var dialogJob: Job? = null

    /**
     * 供子类初始化view
     * @param savedInstanceState Bundle?
     */
    abstract fun initView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewBindingUtil.inflateWithGeneric(this, layoutInflater)
        setContentView(binding.root)
        immersionBar {
            transparentBar()
            titleBar(R.id.title_bar)
            statusBarDarkFont(true)
            navigationBarColor(R.color.transparent)
            navigationBarDarkIcon(true)
            fullScreen(true)
        }
        if (bottomPaddingView() != null) {
            bottomPaddingView()!!.setPadding(0, 0, 0, navigationBarHeight)
        } else {
            binding.root.setPadding(0, 0, 0, navigationBarHeight)
        }

        initView(savedInstanceState)
        observeViewModel()
        NetworkStateManager.mNetworkStateCallback.observe(this) { netType ->
            onNetworkStateChanged(netType)
        }
        initData()
    }

    open fun initData() {}

    /**
     * 虚拟导航栏是否需要自动padding
     * @return Boolean
     */
    open fun bottomPaddingView(): View? = null

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netType: @NetType String) {}

    /**
     * 创建LiveData数据观察者
     */
    abstract fun observeViewModel()

    protected fun <T> observer(flow: Flow<State<T>>, showLoading: Boolean = true, success: (t: T) -> Unit, error: ((code: Int) -> Unit)? = null) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { state ->
                    when (state) {
                        is State.Loading -> if (showLoading) showLoading()
                        is State.Success -> {
                            hideLoading()
                            success.invoke(state.data)
                        }
                        is State.Error -> {
                            hideLoading()
                            showErrToast(state.err)
                            error?.invoke(state.err.errCode)
                        }
                    }
                }
            }
        }
    }

    protected fun <T> observer(flow: Flow<State<T>>, loading: () -> Unit, success: (t: T) -> Unit, error: (() -> Unit)? = null) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { state ->
                    when (state) {
                        is State.Loading -> loading.invoke()
                        is State.Success -> {
                            hideLoading()
                            success.invoke(state.data)
                        }
                        is State.Error -> {
                            hideLoading()
                            showErrToast(state.err)
                            error?.invoke()
                        }
                    }
                }
            }
        }
    }

    protected fun <T> observer(flow: Flow<State<T>>, action: (t: T?) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { state ->
                    when (state) {
                        is State.Success -> action.invoke(state.data)
                        is State.Error -> action.invoke(null)
                        is State.Loading -> {}
                    }
                }
            }
        }
    }

    protected fun <T> observerList(flow: Flow<ListState<T>>, action: (st: ListState<T>) -> Unit) {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { state ->
                    action.invoke(state)
                }
            }
        }
    }

    fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = MaterialDialog(this, TransBehavior)
                .customView(R.layout.dialog_loading)
                .cancelOnTouchOutside(false)
                .cancelable(false)
        }
        if (loadingDialog?.isShowing != true) {
            dialogJob?.cancel()
            dialogJob = lifecycleScope.launch {
                delay(1000)
                loadingDialog?.show()
                delay(5000)
                KLog.d("Holo", "5s超时隐藏")
                hideLoading()
            }
        }
    }

    fun hideLoading() {
        dialogJob?.cancel()
        dialogJob = null
        if (isDestroyed || isFinishing) return
        loadingDialog?.dismiss()
    }
}