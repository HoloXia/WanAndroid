package com.holo.architecture.network

/**
 * @Desc 自定义错误信息异常
 *
 * @Author holo
 * @Date 2022/1/11
 */
data class AppException(
    var errorMsg: String, //错误消息
    var errCode: Int = 0, //错误码
    var errorLog: String = "", //错误日志
    var throwable: Throwable? = null
) : RuntimeException(errorMsg) {

    constructor(msg: String, code: Int) : this(msg, code, "", null)

    constructor(error: HoloError, e: Throwable? = null) : this(error.getValue(), error.getKey(), e?.message ?: "", e)

    override fun toString(): String {
        return "AppException(errorMsg='$errorMsg', errCode=$errCode, errorLog='$errorLog', throwable=${throwable?.message})"
    }


}