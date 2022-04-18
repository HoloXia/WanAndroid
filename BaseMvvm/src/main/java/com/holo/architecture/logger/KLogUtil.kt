package com.holo.architecture.logger

import android.text.TextUtils
import android.util.Log

/**
 * Created on 2020/8/11.
 * @author Holo
 */
internal object KLogUtil {

    fun isEmpty(line: String): Boolean {
        return TextUtils.isEmpty(line) || line == "\n" || line == "\t" || TextUtils.isEmpty(line.trim { it <= ' ' })
    }

    fun printLine(tag: String?, isTop: Boolean) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════")
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════")
        }
    }
}