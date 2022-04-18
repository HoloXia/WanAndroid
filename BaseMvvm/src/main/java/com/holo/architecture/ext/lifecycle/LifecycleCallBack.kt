package com.holo.architecture.ext.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.holo.architecture.logger.KLog

/**
 * @Desc
 * @Author holo
 * @Date 2022/1/10
 */
internal class LifecycleCallBack : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        ActivityStack.pushActivity(activity)
        KLog.d(this::class.java.simpleName, "onActivityCreated : ${activity.localClassName}")
    }

    override fun onActivityStarted(activity: Activity) {
//        KLog.d(this::class.java.simpleName, "onActivityStarted : ${activity.localClassName}")
    }

    override fun onActivityResumed(activity: Activity) {
//        KLog.d(this::class.java.simpleName, "onActivityResumed : ${activity.localClassName}")
    }

    override fun onActivityPaused(activity: Activity) {
//        KLog.d(this::class.java.simpleName, "onActivityPaused : ${activity.localClassName}")
    }


    override fun onActivityDestroyed(activity: Activity) {
        ActivityStack.popActivity(activity)
        KLog.d(this::class.java.simpleName, "onActivityDestroyed : ${activity.localClassName}")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
//        KLog.d(this::class.java.simpleName, "onActivitySaveInstanceState : ${activity.localClassName}")
    }

    override fun onActivityStopped(activity: Activity) {
        KLog.d(this::class.java.simpleName, "onActivityStopped : ${activity.localClassName}")
    }
}