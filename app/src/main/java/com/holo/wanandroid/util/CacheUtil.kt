package com.holo.wanandroid.util

import com.holo.wanandroid.data.dto.UserInfoBean
import com.tencent.mmkv.MMKV

/**
 *
 *
 * @Author holo
 * @Date 2022/4/17
 */
object CacheUtil {

    private const val SP_USER_INFO = "user_info"

    fun saveUserInfo(user: UserInfoBean?) = MMKV.defaultMMKV().encode(SP_USER_INFO, user)

    fun getUserInfo() = MMKV.defaultMMKV().decodeParcelable(SP_USER_INFO, UserInfoBean::class.java)

    fun isLogin() = getUserInfo() != null
}