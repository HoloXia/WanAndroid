package com.holo.wanandroid.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
@Parcelize
data class CategoryBean(
    val author: String = "",
    val children: List<String> = listOf(),
    val courseId: Int = 0,
    val cover: String = "",
    val desc: String = "",
    val id: Int = 0,
    val lisense: String = "",
    val lisenseLink: String = "",
    val name: String = "",
    val order: Int = 0,
    val parentChapterId: Int = 0,
    val userControlSetTop: Boolean = false,
    val visible: Int = 0
) : Parcelable