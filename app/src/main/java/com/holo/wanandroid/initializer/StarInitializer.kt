package com.holo.wanandroid.initializer

import android.content.Context
import androidx.startup.Initializer
import com.holo.architecture.ext.logD
import com.holo.architecture.logger.KLog
import com.holo.wanandroid.BuildConfig
import com.tencent.mmkv.MMKV

/**
 * Androidx Startup初始化组件
 * APP启动初始化三方依赖库
 *
 * @Author holo
 * @Date 2022/3/22
 */
class StarInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        //是否打印日志
        KLog.init(BuildConfig.DEBUG)
        //初始化腾讯mmkv
        val rootDir = MMKV.initialize(context)
        "MMKV root: $rootDir".logD()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}