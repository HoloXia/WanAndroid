package com.holo.wanandroid.data.dto

/**
 *
 *
 * @Author holo
 * @Date 2022/4/18
 */
data class IntegralBean(
    val coinCount: Int = 0,
    val level: Int = 0,
    val nickname: String = "",
    val rank: String = "",
    val userId: Int = 0,
    val username: String = ""
){

    fun getShowName(): String {
        return nickname.ifEmpty { username }
    }
}