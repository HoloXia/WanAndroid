package com.holo.architecture.ext

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Gson拓展
 *
 * @Author holo
 * @Date 2022/3/4
 */

fun <T> Gson.typedToJson(src: T): String = toJson(src)

inline fun <reified T : Any> Gson.fromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)
