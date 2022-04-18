package com.holo.wanandroid

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
@HiltAndroidApp
class WanApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}