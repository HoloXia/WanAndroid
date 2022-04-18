package com.holo.architecture.network

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException

/**
 * @Desc 根据异常返回相关的错误信息工具类
 *
 * @Author holo
 * @Date 2022/1/11
 */
object ExceptionHandle {

    fun handleException(e: Throwable?): AppException {
        e?.printStackTrace()
        val ex: AppException
        e?.let {
            when (it) {
                is HttpException -> {
                    ex = AppException(HoloError.NETWORK_ERROR, e)
                    return ex
                }
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> {
                    ex = AppException(HoloError.PARSE_ERROR, e)
                    return ex
                }
                is ConnectException -> {
                    ex = AppException(HoloError.NETWORK_ERROR, e)
                    return ex
                }
                is javax.net.ssl.SSLException -> {
                    ex = AppException(HoloError.SSL_ERROR, e)
                    return ex
                }
                is ConnectTimeoutException -> {
                    ex = AppException(HoloError.TIMEOUT_ERROR, e)
                    return ex
                }
                is java.net.SocketTimeoutException -> {
                    ex = AppException(HoloError.TIMEOUT_ERROR, e)
                    return ex
                }
                is java.net.UnknownHostException -> {
                    ex = AppException(HoloError.TIMEOUT_ERROR, e)
                    return ex
                }
                is AppException -> return it

                else -> {
                    ex = AppException(HoloError.UNKNOWN, e)
                    return ex
                }
            }
        }
        ex = AppException(HoloError.UNKNOWN, e)
        return ex
    }
}