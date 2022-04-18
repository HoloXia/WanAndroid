package com.holo.wanandroid.network

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
data class PageBean<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)