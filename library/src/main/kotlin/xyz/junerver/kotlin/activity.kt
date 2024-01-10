@file: JvmName("-activity")

package xyz.junerver.kotlin

import android.app.Activity
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

/**
 * Description:
 * @author Junerver
 * date: 2023/5/12-14:02
 * Email: junerver@gmail.com
 * Version: v1.0
 */


/**
 *  隐藏虚拟导航按键，并且全屏
 */
internal fun Window.hideBottomUIMenu() {
    WindowCompat.getInsetsController(this, this.decorView).apply {
        hide(WindowInsetsCompat.Type.navigationBars())
        hide(WindowInsetsCompat.Type.statusBars())
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

/**
 * 隐藏Act的底部虚拟导航按键
 */
fun Activity.hideBottomUIMenu() = window?.hideBottomUIMenu()

/**
 * 全屏，并且收起软键盘
 */
fun Activity.fullScreen() = hideBottomUIMenu().also { hideKeyboard() }

//region 隐藏用户键盘
fun Activity.hideKeyboard() {
    val imm: InputMethodManager? = getSystemService()
    val view = currentFocus ?: View(this)
    imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun Fragment.hideKeyboard() = activity?.hideKeyboard()
//endregion
