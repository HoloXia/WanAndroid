package com.holo.architecture.ext

import com.holo.architecture.logger.KLog

/**
 * @Desc
 * @Author holo
 * @Date 2022/1/10
 */

fun String.logV(tag: String? = null) = KLog.v(tag, this)
fun String.logD(tag: String? = null) = KLog.d(tag, this)
fun String.logI(tag: String? = null) = KLog.i(tag, this)
fun String.logW(tag: String? = null) = KLog.w(tag, this)
fun String.logE(tag: String? = null) = KLog.e(tag, this)
fun String.logA(tag: String? = null) = KLog.a(tag, this)