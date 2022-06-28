package com.holo.wanandroid.ui.discovery

import androidx.lifecycle.viewModelScope
import com.holo.architecture.base.viewmodel.BaseViewModel
import com.holo.architecture.network.ListState
import com.holo.architecture.network.State
import com.holo.wanandroid.data.dto.ArticleBean
import com.holo.wanandroid.data.dto.CategoryBean
import com.holo.wanandroid.data.dto.NavigationBean
import com.holo.wanandroid.data.dto.TreeBean
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
 * @Date 2022/4/16
 */
@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val repo: WanRepository
) : BaseViewModel() {

    private val _plazaListResp = MutableSharedFlow<ListState<ArticleBean>>()
    val plazaListResp = _plazaListResp.asSharedFlow()

    fun getPlazaList(refresh: Boolean = true) {
        if (refresh) pageNo = 0 else pageNo++
        viewModelScope.launch {
            repo.getPlazaList(pageNo).collect { _plazaListResp.emit(it) }
        }
    }

    private val _qaListResp = MutableSharedFlow<ListState<ArticleBean>>()
    val qaListResp = _qaListResp.asSharedFlow()

    fun getQAList(refresh: Boolean = true) {
        if (refresh) pageNo = 0 else pageNo++
        viewModelScope.launch {
            repo.getQAList(pageNo).collect { _qaListResp.emit(it) }
        }
    }

    private val _treeListResp = MutableSharedFlow<List<TreeBean>>()
    val treeListResp = _treeListResp.asSharedFlow()

    fun getTreeList() {
        viewModelScope.launch {
            repo.getTreeList().collect { _treeListResp.emit(it ?: emptyList()) }
        }
    }

    private val _navigationListResp = MutableSharedFlow<List<NavigationBean>>()
    val navigationListResp = _navigationListResp.asSharedFlow()

    fun getNavigationList() {
        viewModelScope.launch {
            repo.getNavigationList().collect { _navigationListResp.emit(it ?: emptyList()) }
        }
    }

    private val _publicNumListResp = MutableSharedFlow<State<List<CategoryBean>>>()
    val publicNumListResp = _publicNumListResp.asSharedFlow()

    fun getPublicNumList() {
        viewModelScope.launch {
            repo.getPublicNumList().collect { _publicNumListResp.emit(it) }
        }
    }

    private val _publicArticleListResp = MutableSharedFlow<ListState<ArticleBean>>()
    val publicArticleListResp = _publicArticleListResp.asSharedFlow()

    fun getPublicArticleList(pnId: Int, refresh: Boolean = true) {
        if (refresh) pageNo = 0 else pageNo++
        viewModelScope.launch {
            repo.getPublicArticleList(pageNo, pnId).collect { _publicArticleListResp.emit(it) }
        }
    }

    private val _treeChildListResp = MutableSharedFlow<ListState<ArticleBean>>()
    val treeChildListResp = _treeChildListResp.asSharedFlow()

    fun getTreeChildList(treeId: Int, refresh: Boolean = true) {
        if (refresh) pageNo = 0 else pageNo++
        viewModelScope.launch {
            repo.getTreeChildList(pageNo, treeId).collect { _treeChildListResp.emit(it) }
        }
    }
}