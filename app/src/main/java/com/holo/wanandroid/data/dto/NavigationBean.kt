package com.holo.wanandroid.data.dto

/**
 *
 *
 * @Author holo
 * @Date 2022/4/16
 */
data class NavigationBean(
    val articles: List<ArticleBean> = listOf(),
    val cid: Int = 0,
    val name: String = ""
)