package com.holo.architecture.initializer

import android.app.Application
import android.content.Context
import android.net.NetworkRequest
import androidx.startup.Initializer
import com.holo.architecture.ext.connectivityManager
import com.holo.architecture.ext.lifecycle.LifecycleCallBack
import com.holo.architecture.network.NetworkStateCallback

val appContext: Application by lazy { BaseInitializer.app }

/**
 * @Desc
 * @Author holo
 * @Date 2022/1/10
 */
class BaseInitializer : Initializer<Unit> {
    internal companion object {
        lateinit var app: Application
    }

    override fun create(context: Context) {
        app = context.applicationContext as Application
        app.registerActivityLifecycleCallbacks(LifecycleCallBack())

        //监听网络变化状态
        appContext.connectivityManager?.registerNetworkCallback(NetworkRequest.Builder().build(), NetworkStateCallback(app))
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}