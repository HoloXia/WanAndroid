package com.holo.architecture.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.holo.architecture.network.AppException
import java.io.Serializable

/**
 * 启动新的Activity，同[Activity.startActivity]
 *
 * 请注意，如果从 [android.app.Activity] 上下文外部调用此方法，则 Intent 必须包含 [Intent.FLAG_ACTIVITY_NEW_TASK] 启动标志。
 * 这是因为，如果不是从现有的 Activity 开始，就没有现有的任务可以放置新的 Activity，因此需要将其放置在自己的单独任务中。
 *
 *  ```
 *  示例：
 *      //不携带参数
 *      startActivity<TestActivity>()
 *
 *      //携带参数（可连续多个键值对）
 *      startActivity<TestActivity>("Key" to "Value")
 *  ```
 *
 * @param params extras键值对
 * @receiver Context
 * @param params Array<out Pair<String, Any?>>
 */
inline fun <reified T : AppCompatActivity> Context.startActivity(vararg params: Pair<String, Any?>) =
    this.startActivity(Intent(this, T::class.java).putExtras(params))


/**
 * Fragment启动新的Activity，同[Activity.startActivity]
 *
 *  ```
 *  示例：
 *      //不携带参数
 *      startActivity<TestActivity>()
 *
 *      //携带参数（可连续多个键值对）
 *      startActivity<TestActivity>("Key" to "Value")
 *  ```
 *
 * @param params extras键值对
 * @receiver Context
 * @param params Array<out Pair<String, Any?>>
 */
inline fun <reified T : AppCompatActivity> Fragment.startActivity(vararg params: Pair<String, Any?>) =
    this.startActivity(Intent(this.activity, T::class.java).putExtras(params))

/**
 * 启动新的Activity并返回结果
 *
 *  ```
 *  示例：
 *      //不携带参数
 *      startActivityForResult<MainActivity>{resultCode, result ->  }
 *
 *      //携带参数（可连续多个键值对）
 *      startActivityForResult<MainActivity>("Key" to "Value"){resultCode, result ->  }
 *  ```
 *
 * @param params extras键值对
 * @param callback 目标[Activity.setResult]之后回调
 * @receiver Context
 * @param params Array<out Pair<String, Any?>>
 */
inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivityForResult(
    vararg params: Pair<String, Any?>,
    crossinline callback: ((resultCode: Int, result: Intent?) -> Unit)
) = this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    callback.invoke(it.resultCode, it.data)
}.launch(Intent(this, T::class.java).putExtras(params))


/**
 * Fragment启动新的Activity并返回结果
 *
 *  ```
 *  示例：
 *      //不携带参数
 *      startActivityForResult<MainActivity>{resultCode, result ->  }
 *
 *      //携带参数（可连续多个键值对）
 *      startActivityForResult<MainActivity>("Key" to "Value"){resultCode, result ->  }
 *  ```
 *
 * @param params extras键值对
 * @param callback 目标[Activity.setResult]之后回调
 * @receiver Context
 * @param params Array<out Pair<String, Any?>>
 */
inline fun <reified T : Activity> Fragment.startActivityForResult(
    vararg params: Pair<String, Any?>,
    crossinline callback: ((resultCode: Int, result: Intent?) -> Unit)
) = this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    callback.invoke(it.resultCode, it.data)
}.launch(Intent(this.activity, T::class.java).putExtras(params))


fun Intent.putExtras(params: Array<out Pair<String, Any?>>): Intent {
    if (params.isEmpty()) return this
    params.forEach {
        when (val value = it.second) {
            null -> putExtra(it.first, null as Serializable?)
            is Int -> putExtra(it.first, value)
            is Long -> putExtra(it.first, value)
            is CharSequence -> putExtra(it.first, value)
            is String -> putExtra(it.first, value)
            is Float -> putExtra(it.first, value)
            is Double -> putExtra(it.first, value)
            is Char -> putExtra(it.first, value)
            is Short -> putExtra(it.first, value)
            is Boolean -> putExtra(it.first, value)
            is Serializable -> putExtra(it.first, value)
            is Bundle -> putExtra(it.first, value)
            is Parcelable -> putExtra(it.first, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> putExtra(it.first, value)
                value.isArrayOf<String>() -> putExtra(it.first, value)
                value.isArrayOf<Parcelable>() -> putExtra(it.first, value)
                else -> throw AppException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            is IntArray -> putExtra(it.first, value)
            is LongArray -> putExtra(it.first, value)
            is FloatArray -> putExtra(it.first, value)
            is DoubleArray -> putExtra(it.first, value)
            is CharArray -> putExtra(it.first, value)
            is ShortArray -> putExtra(it.first, value)
            is BooleanArray -> putExtra(it.first, value)
            else -> throw AppException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
        }
        return@forEach
    }
    return this
}