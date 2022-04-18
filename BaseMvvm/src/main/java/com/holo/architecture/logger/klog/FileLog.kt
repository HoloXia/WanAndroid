package com.holo.architecture.logger.klog

import android.util.Log
import java.io.*
import java.util.*

/**
 * Created on 2020/8/11.
 * @author Holo
 */
internal object FileLog {

    private const val FILE_PREFIX = "KLog_"
    private const val FILE_FORMAT = ".log"

    fun printFile(
        tag: String?,
        targetDirectory: File,
        fileName: String?,
        headString: String,
        msg: String
    ) {
        if (save(targetDirectory, fileName ?: getFileName(), msg)) {
            Log.d(tag, headString + " save log success ! location is >>>" + targetDirectory.absolutePath + "/" + fileName)
        } else {
            Log.e(tag, headString + "save log fails !")
        }
    }

    private fun save(dic: File, fileName: String, msg: String): Boolean {
        val file = File(dic, fileName)
        return try {
            val outputStream: OutputStream = FileOutputStream(file)
            val outputStreamWriter = OutputStreamWriter(outputStream, "UTF-8")
            outputStreamWriter.write(msg)
            outputStreamWriter.flush()
            outputStream.close()
            true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            false
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            false
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun getFileName(): String {
        val random = Random()
        return FILE_PREFIX + java.lang.Long.toString(System.currentTimeMillis() + random.nextInt(10000))
            .substring(4) + FILE_FORMAT
    }
}