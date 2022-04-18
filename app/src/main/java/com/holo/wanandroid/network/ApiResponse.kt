package com.holo.wanandroid.network

import com.holo.architecture.network.BaseResponse

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
data class ApiResponse<T>(
    val errorCode: Int = 0,
    val data: T,
    val errorMsg: String? = ""
) : BaseResponse<T>() {

    override fun isSuccess() = errorCode == 0

    override fun getResponseData() = data

    override fun getResponseCode() = errorCode

    override fun getResponseMsg() = errorMsg ?: ""
}
