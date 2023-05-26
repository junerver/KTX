@file: JvmName(" runonui")

package com.edusoa.android.kotlin

import android.app.Activity
import androidx.fragment.app.Fragment
import android.os.Handler
import android.os.Looper

/**
 * Description:
 * @author Junerver
 * date: 2023/5/11-9:36
 * Email: junerver@gmail.com
 * Version: v1.0
 */

fun <T> T.postUI(action: () -> Unit) {
    // Fragment
    if (this is Fragment) {
        val fragment = this
        if (!fragment.isAdded) return
        val activity = fragment.activity ?: return
        if (activity.isFinishing) return
        activity.runOnUiThread(action)
        return
    }
    // Activity
    if (this is Activity) {
        if (this.isFinishing) return

        this.runOnUiThread(action)
        return
    }
    // 主线程
    if (Looper.getMainLooper() === Looper.myLooper()) {
        action()
        return
    }
    // 子线程，使用handler
    KitUtil.handler.post { action() }
}

object KitUtil {
    val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
}