package com.holo.wanandroid.ui.mine

import androidx.lifecycle.viewModelScope
import com.holo.architecture.base.viewmodel.BaseViewModel
import com.holo.architecture.ext.vibrator
import com.holo.architecture.initializer.appContext
import com.holo.architecture.network.ListState
import com.holo.architecture.network.State
import com.holo.wanandroid.data.dto.CollectBean
import com.holo.wanandroid.repository.WanRepository
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
class CollectViewModel @Inject constructor(
    private val repo: WanRepository
) : BaseViewModel() {

    private val _collectResp = MutableSharedFlow<State<Int>>()
    val collectResp = _collectResp.asSharedFlow()

    fun collect(id: Int, position: Int) {
        appContext.vibrator?.vibrate(20)
        viewModelScope.launch {
            repo.collect(id).collect {
                _collectResp.emit(
                    when (it) {
                        is State.Loading -> State.loading()
                        is State.Error -> State.error(it.err)
                        else -> State.success(position)
                    }
                )
            }
        }
    }

    private val _unCollectResp = MutableSharedFlow<State<Int>>()
    val unCollectResp = _unCollectResp.asSharedFlow()

    fun unCollect(id: Int, position: Int) {
        appContext.vibrator?.vibrate(20)
        viewModelScope.launch {
            repo.unCollect(id).collect {
                _unCollectResp.emit(
                    when (it) {
                        is State.Loading -> State.loading()
                        is State.Error -> State.error(it.err)
                        else -> State.success(position)
                    }
                )
            }
        }
    }

    fun infoUnCollect(id: Int, originId: Int, position: Int) {
        appContext.vibrator?.vibrate(20)
        viewModelScope.launch {
            repo.infoUnCollect(id, if (originId == 0) -1 else originId).collect {
                _unCollectResp.emit(
                    when (it) {
                        is State.Loading -> State.loading()
                        is State.Error -> State.error(it.err)
                        else -> State.success(position)
                    }
                )
            }
        }
    }

    private val _collectListResp = MutableSharedFlow<ListState<CollectBean>>()
    val collectListResp = _collectListResp.asSharedFlow()

    fun getCollectList(refresh: Boolean = true) {
        if (refresh) pageNo = 0 else pageNo++
        viewModelScope.launch {
            repo.getCollectList(pageNo).collect { _collectListResp.emit(it) }
        }
    }
}