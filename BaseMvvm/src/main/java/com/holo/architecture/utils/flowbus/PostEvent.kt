package com.holo.architecture.utils.flowbus

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

/**
 * Application全局范围的事件
 * ```
 * 示例：
 *      postEvent(AppScopeEvent("form TestFragment"))
 * ```
 * @param event T 事件
 * @param timeMillis Long 延迟发送，毫秒
 */
inline fun <reified T> postEvent(event: T, timeMillis: Long = 0L) {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .postEvent(T::class.java.name, event!!, timeMillis)
}


/**
 * 限定范围的事件
 * ```
 * 示例：
 *      //fragment 内部范围
 *      postEvent(fragment,FragmentEvent("form TestFragment"))
 *
 *      //Activity 内部范围
 *      postEvent(requireActivity(),ActivityEvent("form TestFragment"))
 * ```
 * @param event T 事件
 * @param timeMillis Long 延迟发送，毫秒
 */
inline fun <reified T> postEvent(scope: ViewModelStoreOwner, event: T, timeMillis: Long = 0L) {
    ViewModelProvider(scope).get(EventBusCore::class.java)
        .postEvent(T::class.java.name, event!!, timeMillis)
}