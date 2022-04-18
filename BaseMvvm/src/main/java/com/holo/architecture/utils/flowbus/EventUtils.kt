package com.holo.architecture.utils.flowbus

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * 获取事件监听者数量
 * ```
 *      getEventObserverCount(T::class.java)
 * ```
 * @param event Class<T>
 * @return Int
 */
inline fun <reified T> getEventObserverCount(event: Class<T>): Int {
    return ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .getEventObserverCount(event.name)
}

/**
 * 获取事件监听者数量
 * ```
 *      getEventObserverCount(fragment, T::class.java)
 *      getEventObserverCount(activity, T::class.java)
 * ```
 * @param scope ViewModelStoreOwner
 * @param event Class<T>
 * @return Int
 */
inline fun <reified T> getEventObserverCount(scope: ViewModelStoreOwner, event: Class<T>): Int {
    return ViewModelProvider(scope).get(EventBusCore::class.java)
        .getEventObserverCount(event.name)
}


/**
 * 移除粘性事件
 *```
 *      removeStickyEvent(T::class.java)
 *```
 * @param event Class<T>
 */
inline fun <reified T> removeStickyEvent(event: Class<T>) {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .removeStickEvent(event.name)
}

/**
 * 移除粘性事件
 *```
 *      removeStickyEvent(fragment, T::class.java)
 *      removeStickyEvent(activity, T::class.java)
 *```
 * @param scope ViewModelStoreOwner
 * @param event Class<T>
 */
inline fun <reified T> removeStickyEvent(scope: ViewModelStoreOwner, event: Class<T>) {
    ViewModelProvider(scope).get(EventBusCore::class.java)
        .removeStickEvent(event.name)
}


/**
 * 清除粘性事件缓存，粘性事件实例还在，但没有了ReplayCache,新观察者不会收到回调
 * ```
 *      clearStickyEvent(T::class.java)
 * ```
 * @param event Class<T>
 */
inline fun <reified T> clearStickyEvent(event: Class<T>) {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .clearStickEvent(event.name)
}

/**
 * 清除粘性事件缓存，粘性事件实例还在，但没有了ReplayCache,新观察者不会收到回调
 *```
 *      clearStickyEvent(fragment, T::class.java)
 *      clearStickyEvent(activity, T::class.java)
 *```
 * @param scope ViewModelStoreOwner
 * @param event Class<T>
 */
inline fun <reified T> clearStickyEvent(scope: ViewModelStoreOwner, event: Class<T>) {
    ViewModelProvider(scope).get(EventBusCore::class.java)
        .clearStickEvent(event.name)
}


/**
 * 生命周期感知
 * @receiver LifecycleOwner
 * @param minState State
 * @param block [@kotlin.ExtensionFunctionType] SuspendFunction1<CoroutineScope, T>
 * @return Job
 */
fun <T> LifecycleOwner.launchWhenStateAtLeast(
    minState: Lifecycle.State,
    block: suspend CoroutineScope.() -> T
): Job {
    return lifecycleScope.launch {
        lifecycle.whenStateAtLeast(minState, block)
    }
}