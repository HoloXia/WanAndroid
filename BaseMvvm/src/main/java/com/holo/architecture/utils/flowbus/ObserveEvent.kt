package com.holo.architecture.utils.flowbus

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlinx.coroutines.*


/**
 * 监听Application全局范围事件
 * ```
 *      //接收 App Scope事件
 *      observeEvent<AppScopeEvent> {
 *          ...
 *      }
 * ```
 * @receiver LifecycleOwner
 * @param dispatcher CoroutineDispatcher 线程切换，默认主线程[Dispatchers.Main]
 * @param minActiveState State  指定可感知的最小生命周期状态，默认[Lifecycle.State.STARTED]
 * @param isSticky Boolean 是否粘性事件，默认false
 * @param onReceived Function1<T, Unit>
 * @return Job
 */
@MainThread
inline fun <reified T> LifecycleOwner.observeEvent(
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
): Job {
    return ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .observeEvent(
            this,
            T::class.java.name,
            minActiveState,
            dispatcher,
            isSticky,
            onReceived
        )
}

/**
 * 监听Fragment 范围事件
 * ```
 *      //接收 Fragment Scope事件
 *      observeEvent<FragmentEvent>(scope = fragment) {
 *          ...
 *      }
 * ```
 * @param scope Fragment 接收事件的fragment
 * @param dispatcher CoroutineDispatcher 线程切换，默认主线程[Dispatchers.Main]
 * @param minActiveState State  指定可感知的最小生命周期状态，默认[Lifecycle.State.STARTED]
 * @param isSticky Boolean 是否粘性事件，默认false
 * @param onReceived Function1<T, Unit>
 * @return Job
 */
@MainThread
inline fun <reified T> observeEvent(
    scope: Fragment,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
): Job {
    return ViewModelProvider(scope).get(EventBusCore::class.java)
        .observeEvent(
            scope,
            T::class.java.name,
            minActiveState,
            dispatcher,
            isSticky,
            onReceived
        )
}

/**
 * Fragment 监听Activity 范围事件
 * ```
 *      //接收 Activity Scope事件
 *      observeEvent<ActivityEvent>(scope = requireActivity()) {
 *          ...
 *      }
 * ```
 * @param scope ComponentActivity Activity
 * @param dispatcher CoroutineDispatcher 线程切换，默认主线程[Dispatchers.Main]
 * @param minActiveState State  指定可感知的最小生命周期状态，默认[Lifecycle.State.STARTED]
 * @param isSticky Boolean 是否粘性事件，默认false
 * @param onReceived Function1<T, Unit>
 * @return Job
 */
@MainThread
inline fun <reified T> observeEvent(
    scope: ComponentActivity,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
): Job {
    return ViewModelProvider(scope).get(EventBusCore::class.java)
        .observeEvent(
            scope,
            T::class.java.name,
            minActiveState,
            dispatcher,
            isSticky,
            onReceived
        )
}

@MainThread
inline fun <reified T> observeEvent(
    coroutineScope: CoroutineScope,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
): Job {
    return coroutineScope.launch {
        ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
            .observeWithoutLifecycle(
                T::class.java.name,
                isSticky,
                onReceived
            )
    }
}

@MainThread
inline fun <reified T> observeEvent(
    scope: ViewModelStoreOwner,
    coroutineScope: CoroutineScope,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
): Job {
    return coroutineScope.launch {
        ViewModelProvider(scope).get(EventBusCore::class.java)
            .observeWithoutLifecycle(
                T::class.java.name,
                isSticky,
                onReceived
            )
    }
}