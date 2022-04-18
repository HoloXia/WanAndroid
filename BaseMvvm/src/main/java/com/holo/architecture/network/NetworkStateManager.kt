package com.holo.architecture.network

import com.holo.architecture.utils.netstatus.NetType
import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 * 网络状态变化LiveData监听
 *
 * @Author holo
 * @Date 2022/1/14
 */
object NetworkStateManager {

    val mNetworkStateCallback = UnPeekLiveData<@NetType String>()

    var netType: String = NetType.NONE

}