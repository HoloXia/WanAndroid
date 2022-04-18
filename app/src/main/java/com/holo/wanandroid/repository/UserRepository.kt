package com.holo.wanandroid.repository

import com.holo.architecture.base.repository.IRepository
import com.holo.wanandroid.data.remote.UserRemoteData
import com.holo.wanandroid.ext.toFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 *
 *
 * @Author holo
 * @Date 2022/4/17
 */
class UserRepository @Inject constructor(
    private val remote: UserRemoteData,
    private val ioDispatcher: CoroutineDispatcher
) : IRepository {

    suspend fun doLogin(account: String, psw: String) = toFlow(ioDispatcher) { remote.doLogin(account, psw) }

    suspend fun doLogout() = toFlow(ioDispatcher) { remote.doLogout() }

    suspend fun doRegister(account: String, psw: String, rePsw: String) = toFlow(ioDispatcher) { remote.doRegister(account, psw, rePsw) }

    suspend fun getMyIntegral() = flow { emit(remote.getMyIntegral()) }.flowOn(ioDispatcher)

    suspend fun getMyIntegralList(page: Int, refresh: Boolean) = flow { emit(remote.getMyIntegralList(page, refresh)) }.flowOn(ioDispatcher)

    suspend fun getIntegralRankList(page: Int, refresh: Boolean) = flow { emit(remote.getIntegralRankList(page, refresh)) }.flowOn(ioDispatcher)
}