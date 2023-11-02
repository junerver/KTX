@file: JvmName("-view")

package com.edusoa.android.kotlin


import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible


/**
 * dp转px
 */
fun View.dp2px(dipValue: Float): Float {
    return (dipValue * this.resources.displayMetrics.density + 0.5f)
}

/**
 * px转dp
 */
fun View.px2dp(pxValue: Float): Float {
    return (pxValue / this.resources.displayMetrics.density + 0.5f)
}

/**
 * sp转px
 */
fun View.sp2px(spValue: Float): Float {
    return (spValue * this.resources.displayMetrics.scaledDensity + 0.5f)
}

/**
 * 一个防抖的点击事件
 */
inline fun View.setSingleClickListener(wait: Int = 500, crossinline block: (View) -> Unit) {
    var lastTime = 0L
    setOnClickListener {
        try {
            if (System.currentTimeMillis() - lastTime < wait) return@setOnClickListener
            block(it)
        } finally {
            lastTime = System.currentTimeMillis()
        }
    }
}

//region 可见性扩展函数
fun View.gone() {
    this.isGone = true
}

infix fun View.goneIf(condition: Boolean) = runIf(condition) { this.gone() }

fun gones(vararg views: View?) {
    views.forEach {
        it?.gone()
    }
}

fun View.invisible() {
    this.isInvisible = true
}

infix fun View.invisibleIf(condition: Boolean) = runIf(condition) { this.invisible() }

fun invisibles(vararg views: View) {
    views.forEach {
        it.invisible()
    }
}

fun View.visible() {
    this.isVisible = true
}

infix fun View.visibleIf(condition: Boolean) = runIf(condition) { this.visible() }

fun visibles(vararg views: View) {
    views.forEach {
        it.visible()
    }
}
//endregion
