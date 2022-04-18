package com.holo.architecture.logger

import android.text.TextUtils
import android.util.Log
import com.holo.architecture.logger.klog.BaseLog.printDefault
import com.holo.architecture.logger.klog.FileLog.printFile
import com.holo.architecture.logger.klog.JsonLog
import com.holo.architecture.logger.klog.XmlLog
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created on 2020/8/11.
 * @author Holo8
 */
object KLog {

    internal val LINE_SEPARATOR = System.getProperty("line.separator")
    internal const val NULL_TIPS = "Log with null object"

    private const val PARAM = "Param"
    private const val NULL = "null"
    private const val TAG_DEFAULT = "KLog"

    internal const val JSON_INDENT = 4

    internal const val V = 0x1
    internal const val D = 0x2
    internal const val I = 0x3
    internal const val W = 0x4
    internal const val E = 0x5
    internal const val A = 0x6

    private const val JSON = 0x7
    private const val XML = 0x8

    private const val STACK_TRACE_INDEX_5 = 5
    private const val STACK_TRACE_INDEX_4 = 4

    private var mGlobalTag: String? = null
    private var mIsGlobalTagEmpty = true
    private var IS_SHOW_LOG = true

    fun init(isShowLog: Boolean, tag: String? = null) {
        IS_SHOW_LOG = isShowLog
        mGlobalTag = tag
        mIsGlobalTagEmpty = mGlobalTag.isNullOrEmpty()
    }

    fun v(msg: String?) {
        printLog(V, null, msg)
    }

    fun v(tag: String?, vararg objects: String?) {
        printLog(V, tag, *objects)
    }

    fun d(msg: String?) {
        printLog(D, null, msg)
    }

    fun d(tag: String?, vararg objects: String?) {
        printLog(D, tag, *objects)
    }

    fun i(msg: String?) {
        printLog(I, null, msg)
    }

    fun i(tag: String?, vararg objects: String?) {
        printLog(I, tag, *objects)
    }

    fun w(msg: String?) {
        printLog(W, null, msg)
    }

    fun w(tag: String?, vararg objects: String?) {
        printLog(W, tag, *objects)
    }

    fun e(msg: String?) {
        printLog(E, null, msg)
    }

    fun e(tr: Throwable) {
        e(null, "", tr)
    }

    fun e(tag: String?, msg: String, tr: Throwable) {
        printLog(E, tag, msg.plus("\n").plus(tr.message).plus("\n").plus(Log.getStackTraceString(tr)))
    }

    fun e(tag: String?, vararg objects: String?) {
        printLog(E, tag, *objects)
    }

    fun a(msg: String?) {
        printLog(A, null, msg)
    }

    fun a(tag: String?, vararg objects: String?) {
        printLog(A, tag, *objects)
    }

    fun json(jsonFormat: String?) {
        printLog(JSON, null, jsonFormat)
    }

    fun json(tag: String?, jsonFormat: String?) {
        printLog(JSON, tag, jsonFormat)
    }

    fun xml(xml: String?) {
        printLog(XML, null, xml)
    }

    fun xml(tag: String?, xml: String?) {
        printLog(XML, tag, xml)
    }

    fun file(targetDirectory: File, msg: String) {
        printFile(null, targetDirectory, null, msg)
    }

    fun file(tag: String?, targetDirectory: File, msg: String) {
        printFile(tag, targetDirectory, null, msg)
    }

    fun file(tag: String?, targetDirectory: File, fileName: String?, msg: String) {
        printFile(tag, targetDirectory, fileName, msg)
    }

    fun debug(msg: String?) {
        printDebug(null, msg)
    }

    fun debug(tag: String?, vararg objects: String?) {
        printDebug(tag, *objects)
    }

    fun trace() {
        printStackTrace()
    }

    private fun printStackTrace() {
        if (!IS_SHOW_LOG) {
            return
        }
        val tr = Throwable()
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        val message = sw.toString()
        val traceString = message.split("\\n\\t".toRegex()).toTypedArray()
        val sb = java.lang.StringBuilder()
        sb.append("\n")
        for (trace in traceString) {
            if (trace.contains("at KLog")) {
                continue
            }
            sb.append(trace).append("\n")
        }
        val contents = wrapperContent(STACK_TRACE_INDEX_4, null, sb.toString())
        val tag = contents[0]
        val msg = contents[1]
        val headString = contents[2]
        printDefault(D, tag, headString + msg)
    }

    private fun printLog(type: Int, tagStr: String?, vararg messages: String?) {
        if (!IS_SHOW_LOG) {
            return;
        }
        val contents: Array<String> = wrapperContent(STACK_TRACE_INDEX_5, tagStr, *messages)
        val tag = contents[0]
        val msg = contents[1]
        val headString = contents[2]
        when (type) {
            V, D, I, W, E, A -> {
                printDefault(type, tag, headString + msg)
            }
            JSON -> JsonLog.printJson(tag, msg, headString)
            XML -> XmlLog.printXml(tag, msg, headString)
        }
    }

    private fun wrapperContent(
        stackTraceIndex: Int,
        tagStr: String?,
        vararg messages: String?
    ): Array<String> {
        val stackTrace = Thread.currentThread().stackTrace
        val targetElement = stackTrace[stackTraceIndex]

        val fileName = targetElement.fileName
        var className = targetElement.className
        val suffix = fileName.substring(fileName.lastIndexOf("."))
        val classNameInfo = className.split("\\.".toRegex())

        if (classNameInfo.isNotEmpty()) {
            className = classNameInfo[classNameInfo.size - 1] + suffix
        }

        if (className.contains("$")) {
            className = className.split("\\$".toRegex())[0] + suffix
        }

        val methodName = targetElement.methodName
        var lineNumber = targetElement.lineNumber

        if (lineNumber < 0) {
            lineNumber = 0
        }

        var tag = tagStr ?: className

        if (mIsGlobalTagEmpty && TextUtils.isEmpty(tag)) {
            tag = TAG_DEFAULT
        } else if (!mIsGlobalTagEmpty) {
            tag = mGlobalTag
        }

        val msg = getObjectsString(*messages)
        val headString = "[ ($className:$lineNumber)#$methodName ] "
        return arrayOf(tag, msg, headString)
    }

    private fun getObjectsString(vararg messages: String?): String {
        when {
            messages.isNullOrEmpty() -> {
                return NULL_TIPS
            }
            messages.size > 1 -> {
                val stringBuilder = StringBuilder()
                stringBuilder.append("\n")
                for (i in messages.indices) {
                    val msg = messages[i]
                    if (msg == null) {
                        stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(
                            NULL
                        ).append("\n")
                    } else {
                        stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(msg.toString()).append("\n")
                    }
                }
                return stringBuilder.toString()
            }
            else -> {
                return messages[0].toString()
            }
        }
    }

    private fun printDebug(tagStr: String?, vararg messages: String?) {
        val contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, *messages)
        val tag = contents[0]
        val msg = contents[1]
        val headString = contents[2]
        printDefault(D, tag, headString + msg)
    }

    private fun printFile(
        tagStr: String?,
        targetDirectory: File,
        fileName: String?,
        objectMsg: String
    ) {
        if (!IS_SHOW_LOG) {
            return
        }
        val contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objectMsg)
        val tag = contents[0]
        val msg = contents[1]
        val headString = contents[2]
        printFile(tag, targetDirectory, fileName, headString, msg)
    }

    internal fun apiLog(cmdId: Int, msg: String) {
        d("Request cmdId:$cmdId, body:$msg")
    }
}