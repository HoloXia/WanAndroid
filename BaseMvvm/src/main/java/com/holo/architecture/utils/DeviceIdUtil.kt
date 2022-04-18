package com.holo.architecture.utils

import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings
import com.holo.architecture.initializer.appContext
import java.security.MessageDigest
import java.util.*


/**
 * 计算设备唯一标识
 *
 * @Author holo
 * @Date 2022/3/23
 */
object DeviceIdUtil {
    /**
     * 获得设备硬件标识
     *
     * @return 设备硬件标识
     */
    fun getDeviceId(): String {
        val sbDeviceId = StringBuilder()

        //获得AndroidId（无需权限）
        val androidId = getAndroidId()
        //获得设备序列号（无需权限）
        val serial = getSERIAL()
        //获得硬件UUID（根据硬件相关属性，生成UUID）（无需权限）
        val uuid = getDeviceUUID().replace("-", "")

        //追加 AndroidId
        if (androidId.isNotEmpty()) {
            sbDeviceId.append(androidId)
            sbDeviceId.append("|")
        }
        //追加serial
        if (serial.isNotEmpty()) {
            sbDeviceId.append(serial)
            sbDeviceId.append("|")
        }
        //追加硬件uuid
        if (uuid.isNotEmpty()) {
            sbDeviceId.append(uuid)
        }

        //生成SHA1，统一DeviceId长度
        if (sbDeviceId.isNotEmpty()) {
            try {
                val hash = getHashByString(sbDeviceId.toString())
                val sha1 = bytesToHex(hash)
                if (sha1.isNotEmpty()) {
                    //返回最终的DeviceId
                    return sha1
                }
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
        }

        //如果以上硬件标识数据均无法获得，
        //则DeviceId默认使用系统随机数，这样保证DeviceId不为空
        return UUID.randomUUID().toString().replace("-", "")
    }

    /**
     * 获得设备的AndroidId
     *
     * @return 设备的AndroidId
     */
    @SuppressLint("HardwareIds")
    private fun getAndroidId(): String {
        try {
            return Settings.Secure.getString(
                appContext.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return ""
    }

    /**
     * 获得设备序列号（如：WTK7N16923005607）, 个别设备无法获取
     * Android 6.0及以上版本是需要动态申请READ_PHONE_STATE权限
     *
     * @return 设备序列号
     */
    private fun getSERIAL(): String {
        try {
            return Build.SERIAL
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return ""
    }

    /**
     * 获得设备硬件uuid
     * 使用硬件信息，计算出一个随机数
     *
     * @return 设备硬件uuid
     */
    private fun getDeviceUUID(): String {
        return try {
            val dev = "3883756".plus(Build.BOARD.length % 10)
                .plus(Build.BRAND.length % 10)
                .plus(Build.DEVICE.length % 10)
                .plus(Build.HARDWARE.length % 10)
                .plus(Build.ID.length % 10)
                .plus(Build.MODEL.length % 10)
                .plus(Build.PRODUCT.length % 10)
                .plus(Build.SERIAL.length % 10)
            UUID(
                dev.hashCode().toLong(),
                Build.SERIAL.hashCode().toLong()
            ).toString()
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            ""
        }
    }

    /**
     * 取SHA1
     * @param data 数据
     * @return 对应的hash值
     */
    private fun getHashByString(data: String): ByteArray {
        return try {
            val messageDigest: MessageDigest = MessageDigest.getInstance("SHA1")
            messageDigest.reset()
            messageDigest.update(data.toByteArray(charset("UTF-8")))
            messageDigest.digest()
        } catch (e: Exception) {
            "".toByteArray()
        }
    }

    /**
     * 转16进制字符串
     * @param data 数据
     * @return 16进制字符串
     */
    private fun bytesToHex(data: ByteArray): String {
        val sb = StringBuilder()
        var stmp: String
        for (n in data.indices) {
            stmp = Integer.toHexString(data[n].toInt() and 0xFF)
            if (stmp.length == 1) sb.append("0")
            sb.append(stmp)
        }
        return sb.toString().toUpperCase(Locale.CHINA)
    }
}