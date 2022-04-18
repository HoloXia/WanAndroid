package com.holo.wanandroid.repository

import com.holo.architecture.base.repository.IRepository
import com.holo.wanandroid.data.remote.WanRemoteData
import com.holo.wanandroid.ext.toFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
class WanRepository @Inject constructor(
    private val remote: WanRemoteData,
    private val ioDispatcher: CoroutineDispatcher
) : IRepository {

    fun getBannerList() = toFlow(ioDispatcher) { remote.getBannerList() }

    fun getTopArticleList() = flow { emit(remote.getTopArticleList()) }.flowOn(ioDispatcher)

    fun getArticleList(page: Int) = flow { emit(remote.getArticleList(page)) }.flowOn(ioDispatcher)

    fun getProjectCategories() = flow { emit(remote.getProjectCategories()) }.flowOn(ioDispatcher)

    fun getProjectList(page: Int, categoryId: Int) = flow { emit(remote.getProjectList(page, categoryId)) }.flowOn(ioDispatcher)

    fun getLatestProjectList(page: Int) = flow { emit(remote.getLatestProjectList(page)) }.flowOn(ioDispatcher)

    suspend fun getPlazaList(page: Int) = flow { emit(remote.getPlazaList(page)) }.flowOn(ioDispatcher)

    suspend fun getQAList(page: Int) = flow { emit(remote.getQAList(page)) }.flowOn(ioDispatcher)

    suspend fun getTreeList() = flow { emit(remote.getTreeList()) }.flowOn(ioDispatcher)

    suspend fun getTreeChildList(page: Int, treeId: Int) = flow { emit(remote.getTreeChildList(page, treeId)) }.flowOn(ioDispatcher)

    suspend fun getNavigationList() = flow { emit(remote.getNavigationList()) }.flowOn(ioDispatcher)

    suspend fun getPublicNumList() = flow { emit(remote.getPublicNumList()) }.flowOn(ioDispatcher)

    suspend fun getPublicArticleList(page: Int, pnId: Int) = flow { emit(remote.getPublicArticleList(page, pnId)) }.flowOn(ioDispatcher)

    suspend fun collect(id: Int) = toFlow(ioDispatcher) { remote.collect(id) }

    suspend fun unCollect(id: Int) = toFlow(ioDispatcher) { remote.unCollect(id) }

    suspend fun infoUnCollect(id: Int, originId: Int) = toFlow(ioDispatcher) { remote.infoUnCollect(id, originId) }

    suspend fun collectUrl(name: String, link: String) = toFlow(ioDispatcher) { remote.collectUrl(name, link) }

    suspend fun unCollectUrl(id: Int) = toFlow(ioDispatcher) { remote.unCollectUrl(id) }

    suspend fun getCollectList(page: Int) = flow { emit(remote.getCollectList(page)) }.flowOn(ioDispatcher)

    suspend fun getCollectUrlList(page: Int) = flow { emit(remote.getCollectUrlList()) }.flowOn(ioDispatcher)
}