package com.holo.wanandroid.ext

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.holo.architecture.initializer.appContext
import com.holo.loadstate.LoadService
import com.holo.loadstate.LoadState
import com.holo.wanandroid.R

/**
 *
 *
 * @Author holo
 * @Date 2022/3/5
 */

fun loadServiceInit(view: View, callback: () -> Unit): LoadService {
    return loadServiceInit(view, R.layout.layout_loadsir_loading, callback)
}

fun loadServiceInit(view: View, @LayoutRes loadLayoutId: Int, callback: () -> Unit): LoadService {
    val loadSir = LoadState.register(view) {
        showLoading()
        callback.invoke()
    }.config {
        loading(loadLayoutId)
        failed(R.layout.layout_loadsir_failed, R.id.btn_retry)
        empty(R.layout.layout_loadsir_empty)
        configColorBuilder {
            setBaseColor(appContext.getColor(R.color.bgShimmer))
            setHighlightColor(appContext.getColor(R.color.white))
        }
    }
    loadSir.showLoading()
    return loadSir
}

fun loadServiceInit(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>, callback: () -> Unit): LoadService {
    return loadServiceInit(recyclerView, adapter, R.layout.layout_loadsir_loading, callback)
}

fun loadServiceInit(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>, @LayoutRes loadLayoutId: Int, callback: () -> Unit): LoadService {
    val loadSir = LoadState.register(recyclerView, adapter) {
        showLoading()
        callback.invoke()
    }.config {
        loading(loadLayoutId)
        failed(R.layout.layout_loadsir_failed, R.id.btn_retry)
        empty(R.layout.layout_loadsir_empty)
        configColorBuilder {
            setBaseColor(appContext.getColor(R.color.bgShimmer))
            setHighlightColor(appContext.getColor(R.color.white))
        }
    }
    loadSir.showLoading()
    return loadSir
}

/**
 * 设置空布局
 */
fun LoadService.showEmptyCus(emptyTip: String? = null, @DrawableRes icon: Int? = null) {
    this.showEmpty().apply {
        emptyTip?.let {
            findViewById<TextView>(R.id.tv_empty).text = it
        }
        icon?.let {
            findViewById<ImageView>(R.id.iv_empty).setImageResource(it)
        }
    }
}

/**
 * 设置错误布局
 * @param message 错误布局显示的提示内容
 */
fun LoadService.showFailedCus(message: String? = null) {
    this.showFailed().apply {
        message?.let {
            this.findViewById<TextView>(R.id.tv_failed).text = message
        }
    }
}

