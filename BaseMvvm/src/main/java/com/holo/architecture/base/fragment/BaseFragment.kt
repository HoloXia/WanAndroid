package com.holo.architecture.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.holo.architecture.base.activity.BaseActivity
import com.holo.architecture.ext.showErrToast
import com.holo.architecture.network.ListState
import com.holo.architecture.network.State
import com.holo.architecture.utils.ViewBindingUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @Desc
 * @Author holo
 * @Date 2022/1/7
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    //是否第一次加载
    protected var isFirst: Boolean = true

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!
    protected var bindNotNull = false

    /**
     * 供子类初始化view
     * @param savedInstanceState Bundle?
     */
    abstract fun initView(savedInstanceState: Bundle?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
        bindNotNull = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        initView(savedInstanceState)
        initData()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindNotNull = false
        _binding = null
    }

    /**
     * 创建观察者
     */
    abstract fun observeViewModel()

    protected fun <T> observer(flow: Flow<State<T>>, showLoading: Boolean = true, success: (t: T) -> Unit, error: (() -> Unit)? = null) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                            error?.invoke()
                        }
                    }
                }
            }
        }
    }

    protected fun <T> observer(flow: Flow<State<T>>, loading: () -> Unit, success: (t: T) -> Unit, error: (() -> Unit)? = null) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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

    protected fun <T> observerObj(flow: Flow<T>, action: (t: T?) -> Unit) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { action.invoke(it) }
            }
        }
    }

    protected fun <T> observerList(flow: Flow<ListState<T>>, action: (st: ListState<T>) -> Unit) {
        lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { state ->
                    action.invoke(state)
                }
            }
        }
    }

    private fun showLoading() {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).showLoading()
        }
    }

    private fun hideLoading() {
        if (activity is BaseActivity<*>) {
            (activity as BaseActivity<*>).hideLoading()
        }
    }

    /**
     * Fragment执行onViewCreated后触发的方法
     */
    open fun initData() {}

    /**
     * 懒加载
     */
    open fun lazyLoadData() {}

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            //延迟加载0.12秒加载 避免fragment跳转动画和渲染ui同时进行，出现些微的小卡顿
            view?.postDelayed({
                lazyLoadData()
                isFirst = false
            }, 120)
        }
    }

}