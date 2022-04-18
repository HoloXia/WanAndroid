package com.holo.wanandroid.event

import com.holo.architecture.network.State
import com.holo.wanandroid.data.dto.UserInfoBean

/**
 *
 *
 * @Author holo
 * @Date 2022/4/18
 */
class LoginEvent(val state: State<UserInfoBean>) {
}