package com.holo.architecture.logger.klog

import android.util.Log
import com.holo.architecture.logger.KLog
import com.holo.architecture.logger.KLogUtil
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created on 2020/8/11.
 * @author Holo
 */
internal object JsonLog {

    fun printJson(tag: String?, msg: String, headString: String) {
        var message: String = try {
            when {
                msg.startsWith("{") -> {
                    val jsonObject = JSONObject(msg)
                    jsonObject.toString(KLog.JSON_INDENT)
                }
                msg.startsWith("[") -> {
                    val jsonArray = JSONArray(msg)
                    jsonArray.toString(KLog.JSON_INDENT)
                }
                else -> {
                    msg
                }
            }
        } catch (e: JSONException) {
            msg
        }
        KLogUtil.printLine(tag, true)
        message = headString + KLog.LINE_SEPARATOR + message
        val lines = message.split(KLog.LINE_SEPARATOR!!).toTypedArray()
        for (line in lines) {
            Log.d(tag, "║ $line")
        }
        KLogUtil.printLine(tag, false)
    }
}