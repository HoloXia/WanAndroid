package com.holo.wanandroid.data.remote

import com.holo.architecture.base.repository.IRemoteData
import com.holo.architecture.network.ListState
import com.holo.architecture.network.State
import com.holo.wanandroid.data.dto.*
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
class WanRemoteData @Inject constructor(private val service: WanService) : IRemoteData {

    suspend fun getBannerList(): State<List<BannerBean>> = processCall { service.getBanner() }

    suspend fun getTopArticleList(): List<ArticleBean>? = processCallObj { service.getTopArticleList() }

    suspend fun getArticleList(page: Int): ListState<ArticleBean> = processListCall(page) { service.getArticleList(page) }

    suspend fun getProjectCategories(): List<CategoryBean>? = processCallObj { service.getProjectCategories() }

    suspend fun getProjectList(page: Int, categoryId: Int): ListState<ProjectBean> = processListCall(page) { service.getProjectList(page, categoryId) }

    suspend fun getLatestProjectList(page: Int): ListState<ProjectBean> = processListCall(page) { service.getLatestProjectList(page) }

    suspend fun getPlazaList(page: Int): ListState<ArticleBean> = processListCall(page) { service.getPlazaList(page) }

    suspend fun getQAList(page: Int): ListState<ArticleBean> = processListCall(page) { service.getQAList(page) }

    suspend fun getTreeList(): List<TreeBean>? = processCallObj { service.getTreeList() }

    suspend fun getTreeChildList(page: Int, treeId: Int): ListState<ArticleBean> = processListCall(page) { service.getTreeChildList(page, treeId) }

    suspend fun getNavigationList(): List<NavigationBean>? = processCallObj { service.getNavigationList() }

    suspend fun getPublicNumList(): State<List<CategoryBean>> = processCall { service.getPublicNumList() }

    suspend fun getPublicArticleList(page: Int, pnId: Int): ListState<ArticleBean> = processListCall(page) { service.getPublicNumArticleList(page, pnId) }

    suspend fun collect(id: Int): State<String?> = processCall { service.collect(id) }

    suspend fun unCollect(id: Int): State<String?> = processCall { service.unCollect(id) }

    suspend fun infoUnCollect(id: Int, originId: Int): State<String?> = processCall { service.infoUnCollect(id, originId) }

    suspend fun collectUrl(name: String, link: String) = processCall { service.collectUrl(name, link) }

    suspend fun unCollectUrl(id: Int) = processCall { service.unCollectUrl(id) }

    suspend fun getCollectList(page: Int): ListState<CollectBean> = processListCall(page) { service.getCollectList(page) }

    suspend fun getCollectUrlList() = processCallObj { service.getCollectUrlList() }
}