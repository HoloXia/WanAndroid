package com.holo.wanandroid.ui.mine

import androidx.lifecycle.viewModelScope
import com.holo.architecture.base.viewmodel.BaseViewModel
import com.holo.architecture.network.ListState
import com.holo.architecture.network.State
import com.holo.architecture.utils.flowbus.postEvent
import com.holo.wanandroid.data.dto.IntegralBean
import com.holo.wanandroid.data.dto.IntegralInfoBean
import com.holo.wanandroid.data.dto.UserInfoBean
import com.holo.wanandroid.event.LoginEvent
import com.holo.wanandroid.event.LogoutEvent
import com.holo.wanandroid.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 *
 * @Author holo
 * @Date 2022/4/17
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: UserRepository
) : BaseViewModel() {

    fun doLogin(account: String, psw: String) {
        viewModelScope.launch {
            repo.doLogin(account, psw).collect {
                postEvent(LoginEvent(it))
            }
        }
    }

    fun doLogout() {
        viewModelScope.launch {
            repo.doLogout().collect {
                postEvent(LogoutEvent(it))
            }
        }
    }

    private val _registerResp = MutableSharedFlow<State<UserInfoBean>>()
    val registerResp = _registerResp.asSharedFlow()

    fun doRegister(account: String, psw: String, rePsw: String) {
        viewModelScope.launch {
            repo.doRegister(account, psw, rePsw).collect { _registerResp.emit(it) }
        }
    }

    private val _myIntegralResp = MutableSharedFlow<IntegralBean>()
    val myIntegralResp = _myIntegralResp.asSharedFlow()

    fun getMyIntegral() {
        viewModelScope.launch {
            repo.getMyIntegral().collect { _myIntegralResp.emit(it ?: IntegralBean()) }
        }
    }

    private val _myIntegralListResp = MutableSharedFlow<ListState<IntegralInfoBean>>()
    val myIntegralListResp = _myIntegralListResp.asSharedFlow()

    fun getMyIntegralList(refresh: Boolean = true) {
        if (refresh) pageNo = 1 else pageNo++
        viewModelScope.launch {
            repo.getMyIntegralList(pageNo, refresh).collect { _myIntegralListResp.emit(it) }
        }
    }

    private val _integralRankListResp = MutableSharedFlow<ListState<IntegralBean>>()
    val integralRankListResp = _integralRankListResp.asSharedFlow()

    fun getIntegralRankList(refresh: Boolean = true) {
        if (refresh) pageNo = 1 else pageNo++
        viewModelScope.launch {
            repo.getIntegralRankList(pageNo, refresh).collect { _integralRankListResp.emit(it) }
        }
    }
}