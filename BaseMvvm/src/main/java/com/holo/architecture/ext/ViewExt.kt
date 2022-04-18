package com.holo.architecture.ext

import android.app.Service
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

/**
 *
 *
 * @Author holo
 * @Date 2022/2/14
 */

fun View.showKeyboard() {
    this.requestFocus()
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun <T : View> T.isVisible(): Boolean {
    return if (this is Button) {
        this.visibility == View.VISIBLE && this.text.trim().isNotBlank()
    } else {
        this.visibility == View.VISIBLE
    }
}

fun <T : View> T.isNotVisible(): Boolean = !isVisible()

fun View.toVisible(visible: Boolean = true) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible(visible: Boolean = true) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun RecyclerView.onChildViewDetachedFromWindow(childViewDetached: (view: View) -> Unit) {
    this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {

        }

        override fun onChildViewDetachedFromWindow(view: View) {
            childViewDetached.invoke(view)
        }
    })
}

/**
 * 检查EditText输入框是否没有输入内容
 *
 * @param message 提示语
 * @return
 */
fun TextView.checkBlank(activity: AppCompatActivity, message: String): String? {
    val text = this.text.toString()
    if (text.isBlank()) {
        activity.showToast(message)
        return null
    }
    return text
}

/**
 * 检查EditText输入框是否没有输入内容
 *
 * @param message 提示语
 * @return
 */
fun TextView.checkBlank(activity: AppCompatActivity, @StringRes message: Int): String? {
    return checkBlank(activity, context.getString(message))
}

/**
 * 检查EditText输入框是否没有输入内容
 *
 * @param message 提示语
 * @return
 */
fun TextView.checkBlank(fragment: Fragment, message: String): String? {
    val text = this.text.toString()
    if (text.isBlank()) {
        fragment.activity?.showToast(message)
        return null
    }
    return text
}

/**
 * 检查EditText输入框是否没有输入内容
 *
 * @param message 提示语
 * @return
 */
fun TextView.checkBlank(fragment: Fragment, @StringRes message: Int): String? {
    return checkBlank(fragment, context.getString(message))
}

fun EditText.isBlank(): Boolean = this.text?.isBlank() == true

fun EditText.isNotBlank(): Boolean = this.text?.isNotBlank() == true


typealias OnBackPressedTypeAlias = () -> Boolean

/**
 * 解决 Fragment 中 OnBackPressed 事件, 默认结束当前Fragment依附的Activity
 * @param type true:结束当前Activity，false：响应callback回调
 */
fun Fragment.setOnHandleBackPressed(type: Boolean = true, callback: OnBackPressedTypeAlias? = null) {
    requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (type) {
                requireActivity().finish()
            } else {
                if (callback?.invoke() == true) {
                    requireActivity().finish()
                }
            }
        }
    })
}

/**
 * 防止重复点击事件 默认0.5秒内不可重复点击
 * @param interval 时间间隔 默认0.2秒
 * @param action 执行方法
 */
var lastClickTime = 0L
fun View.clickNoRepeat(interval: Long = 200, action: (view: View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime != 0L && (currentTime - lastClickTime < interval)) {
            return@setOnClickListener
        }
        lastClickTime = currentTime
        action(it)
    }
}

/**
 * View左右抖动动画
 * @receiver View
 */
fun View.shakeAnimate() {
    this.animate()
        .translationX(20f)
        .setDuration(500)
        .setInterpolator(CycleInterpolator(3f))
        .start()
}