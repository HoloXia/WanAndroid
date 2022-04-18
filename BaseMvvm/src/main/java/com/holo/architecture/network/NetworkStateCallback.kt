package com.holo.architecture.network

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.holo.architecture.logger.KLog
import com.holo.architecture.utils.netstatus.NetType
import com.holo.architecture.utils.netstatus.NetUtils

/**
 * @Desc
 *
 * @Author holo
 * @Date 2022/1/13
 */
class NetworkStateCallback(val application: Application) : ConnectivityManager.NetworkCallback() {

    private companion object {
        const val TAG = "NetworkState"
    }

    // 网络状态广播监听
    private val receiver = NetStatusReceiver()

    init {
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        application.registerReceiver(receiver, filter)
        post(NetUtils.getNetStatus(application))
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        KLog.i(TAG, "net connect success! 网络已连接")
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        KLog.i(TAG, "net disconnect! 网络已断开连接")
        post(NetType.NONE)
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        KLog.i(TAG, "net status change! 网络连接改变")
        // 表明此网络连接成功验证
        val type = NetUtils.getNetStatus(networkCapabilities)
        if (type == NetworkStateManager.mNetworkStateCallback.value) return
        post(type)
    }

    // 执行
    private fun post(str: @NetType String) {
        NetworkStateManager.mNetworkStateCallback.postValue(str)
        NetworkStateManager.netType = str
    }

    /**
     * Receiver方式监听网络状态变化
     * Android 7.0+动态监听还有效
     */
    inner class NetStatusReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            context ?: return
            intent ?: return
            val type = NetUtils.getNetStatus(context)
            if (type == NetworkStateManager.mNetworkStateCallback.value) return
            post(type)
        }
    }
}