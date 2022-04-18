package com.holo.wanandroid.data.dto

import android.os.Parcelable
import com.holo.architecture.base.bean.BaseBean
import kotlinx.parcelize.Parcelize

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
@Parcelize
data class UserInfoBean(
    val admin: Boolean,
    val chapterTops: List<String>,
    val coinCount: Int,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
) : BaseBean(), Parcelable {

    fun getShowName(): String {
        return nickname.ifEmpty { username }
    }
}