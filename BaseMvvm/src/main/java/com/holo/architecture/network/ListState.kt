package com.holo.architecture.network

import com.holo.architecture.base.bean.BaseBean

/**
 * 分页查询数据通用base
 *
 * @Author holo
 * @Date 2022/2/9
 */
data class ListState<T>(
    /**
     * 是否请求成功
     */
    val isSuccess: Boolean,

    /**
     * 是否为刷新
     */
    val isRefresh: Boolean = true,

    /**
     * 是否还有更多
     */
    val hasMore: Boolean = true,

    /**
     * 是否没有数据
     */
    val isEmpty: Boolean = true,

    /**
     * 列表数据
     */
    val listData: List<T> = listOf(),

    /**
     * 数据总条数
     */
    val total:Int = 0,

    /**
     * 错误信息
     */
    val err: AppException = AppException("")
) : BaseBean()