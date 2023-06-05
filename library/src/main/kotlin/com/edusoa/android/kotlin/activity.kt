@file: JvmName("-activity")

package com.edusoa.android.kotlin

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

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
    if (Build.VERSION.SDK_INT in 12..18) { // lower api
        val v = this.window.decorView
        v.systemUiVisibility = View.GONE
    } else if (Build.VERSION.SDK_INT >= 19) {
        //for new api versions.
        val decorView = window.decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
        decorView.systemUiVisibility = uiOptions
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
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
//endregion
