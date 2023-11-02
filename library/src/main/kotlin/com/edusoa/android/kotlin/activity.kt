@file: JvmName("-activity")

package com.edusoa.android.kotlin

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.core.content.getSystemService

/**
 * Description:
 * @author Junerver
 * date: 2023/5/12-14:02
 * Email: junerver@gmail.com
 * Version: v1.0
 */

/**
 * @Description 隐藏Act的底部虚拟键盘
 * @Author Junerver
 * Created at 2019-06-26 11:04
 * @param
 * @return
 */
fun Activity.hideBottomUIMenu() {
    //隐藏虚拟按键，并且全屏
    WindowCompat.getInsetsController(window, window.decorView).apply {
        hide(WindowInsetsCompat.Type.navigationBars())
        hide(WindowInsetsCompat.Type.statusBars())
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

//region 隐藏用户键盘
fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun Fragment.hideKeyboard() {
    activity?.apply {
        val imm: InputMethodManager? = getSystemService()
        val view = currentFocus ?: View(this)
        imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
//endregion
