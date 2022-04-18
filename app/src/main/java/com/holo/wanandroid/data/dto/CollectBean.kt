package com.holo.wanandroid.data.dto

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 *
 *
 * @Author holo
 * @Date 2022/4/17
 */
data class CollectBean(
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val originId: Int,
    val publishTime: Long,
    val title: String,
    val userId: Int,
    val visible: Int,
    val zan: Int
) : MultiItemEntity {

    override val itemType: Int
        get() = if (envelopePic.isNullOrEmpty()) CollectType.COLLECT_ARTICLE else CollectType.COLLECT_PROJECTS
}

object CollectType {
    const val COLLECT_ARTICLE = 0
    const val COLLECT_PROJECTS = 1
}