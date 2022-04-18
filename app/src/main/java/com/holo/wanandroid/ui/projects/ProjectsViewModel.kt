package com.holo.wanandroid.ui.projects

import androidx.lifecycle.viewModelScope
import com.holo.architecture.base.viewmodel.BaseViewModel
import com.holo.architecture.network.ListState
import com.holo.wanandroid.data.dto.ProjectBean
import com.holo.wanandroid.data.dto.CategoryBean
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
 * @Date 2022/4/15
 */
@HiltViewModel
data class ProjectsViewModel @Inject constructor(
    private val repo: WanRepository
) : BaseViewModel() {

    private val _categoryListResp = MutableSharedFlow<List<CategoryBean>>()
    val categoryListResp = _categoryListResp.asSharedFlow()

    fun getProjectCategories() {
        viewModelScope.launch {
            repo.getProjectCategories().collect { _categoryListResp.emit(it ?: emptyList()) }
        }
    }

    private val _projectListResp = MutableSharedFlow<ListState<ProjectBean>>()
    val projectListResp = _projectListResp.asSharedFlow()

    fun getProjectList(categoryId: Int, refresh: Boolean = true) {
        if (refresh) pageNo = 0 else pageNo++
        viewModelScope.launch {
            if (categoryId == -1) {
                repo.getLatestProjectList(pageNo)
            } else {
                repo.getProjectList(pageNo, categoryId)
            }.collect { _projectListResp.emit(it) }
        }
    }
}