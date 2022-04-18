package com.holo.wanandroid.ui.home

import androidx.lifecycle.viewModelScope
import com.holo.architecture.base.viewmodel.BaseViewModel
import com.holo.architecture.network.ListState
import com.holo.architecture.network.State
import com.holo.wanandroid.data.dto.ArticleBean
import com.holo.wanandroid.data.dto.BannerBean
import com.holo.wanandroid.repository.WanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: WanRepository
) : BaseViewModel() {

    private val _bannerListResp = MutableSharedFlow<State<List<BannerBean>>>()
    val bannerListResp = _bannerListResp.asSharedFlow()

    fun getBannerList() {
        viewModelScope.launch {
            repo.getBannerList().collect { _bannerListResp.emit(it) }
        }
    }

    private val _articleListResp = MutableSharedFlow<ListState<ArticleBean>>()
    val articleListResp = _articleListResp.asSharedFlow()

    fun getArticleList(refresh: Boolean = true) {
        if (refresh) pageNo = 0 else pageNo++
        viewModelScope.launch {
            val listFlow = repo.getArticleList(pageNo)
            val topFlow = if (refresh) repo.getTopArticleList() else flow { emit(emptyList()) }
            listFlow.zip(topFlow) { list, top ->
                if (top.isNullOrEmpty()) {
                    list
                } else {
                    if (refresh) {
                        val temp = mutableListOf<ArticleBean>()
                        temp.addAll(top)
                        temp.addAll(list.listData)
                        ListState(true, refresh, list.hasMore, temp.isEmpty(), temp)
                    } else {
                        list
                    }
                }
            }.collect {
                _articleListResp.emit(it)
            }
        }
    }
}