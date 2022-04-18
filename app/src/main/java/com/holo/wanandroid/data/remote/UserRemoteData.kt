package com.holo.wanandroid.data.remote

import com.holo.architecture.base.repository.IRemoteData
import com.holo.architecture.network.ListState
import com.holo.architecture.network.State
import com.holo.wanandroid.data.dto.IntegralBean
import com.holo.wanandroid.data.dto.IntegralInfoBean
import com.holo.wanandroid.data.dto.UserInfoBean
import com.holo.wanandroid.ext.processCall
import com.holo.wanandroid.ext.processCallObj
import com.holo.wanandroid.ext.processListCall
import com.holo.wanandroid.network.WanService
import javax.inject.Inject

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
class UserRemoteData @Inject constructor(private val service: WanService) : IRemoteData {

    suspend fun doLogin(account: String, psw: String): State<UserInfoBean> = processCall { service.login(account, psw) }

    suspend fun doLogout(): State<String?> = processCall { service.logout() }

    suspend fun doRegister(account: String, psw: String, rePsw: String): State<UserInfoBean> = processCall { service.register(account, psw, rePsw) }

    suspend fun getMyIntegral(): IntegralBean? = processCallObj { service.getMyIntegral() }

    suspend fun getMyIntegralList(page: Int, refresh: Boolean): ListState<IntegralInfoBean> = processListCall(refresh) { service.getMyIntegralList(page) }

    suspend fun getIntegralRankList(page: Int, refresh: Boolean): ListState<IntegralBean> = processListCall(refresh) { service.getIntegralRankList(page) }
}