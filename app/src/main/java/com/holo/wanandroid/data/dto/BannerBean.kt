package com.holo.wanandroid.data.dto

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
data class BannerBean(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)