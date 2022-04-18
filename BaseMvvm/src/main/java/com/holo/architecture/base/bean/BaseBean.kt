package com.holo.architecture.base.bean

import com.google.gson.Gson

/**
 *
 *
 * @Author holo
 * @Date 2022/1/15
 */
open class BaseBean {

    /**
     * 重写toString方法，对象直接打印json串
     * @return String
     */
    override fun toString(): String {
        return Gson().toJson(this)
    }
}