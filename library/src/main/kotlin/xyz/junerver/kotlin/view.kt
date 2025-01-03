@file: JvmName("-view")

package xyz.junerver.kotlin


import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
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

fun Array<out View>.gone() {
    this.forEach {
        it.gone()
    }
}

fun View.invisible() {
    this.isInvisible = true
}

fun Array<out View>.invisible() {
    this.forEach {
        it.invisible()
    }
}

fun View.visible() {
    this.isVisible = true
}

fun Array<out View>.visible() {
    this.forEach {
        it.visible()
    }
}

infix fun View.goneIf(condition: Boolean) = runIf(condition) { this.gone() }

infix fun View.invisibleIf(condition: Boolean) = runIf(condition) { this.invisible() }

infix fun View.visibleIf(condition: Boolean) = runIf(condition) { this.visible() }

infix fun View.visibleOrInIf(condition: Boolean) =
    if (condition) this.visible() else this.invisible()

infix fun View.visibleOrGoneIf(condition: Boolean) = if (condition) this.visible() else this.gone()

//endregion

/**
 * 强制打开输入软键盘
 */
fun View.showKeyboardForced() {
    val imm:InputMethodManager? = this.context.getSystemService()
    imm?.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}
/**
 * 扩展方法，扩大点击区域
 * NOTE: 需要保证目标targetView有父View，否则无法扩大点击区域
 *
 * @param expandSize 扩大的大小，单位px
 *
 * ```kotlin
 * private val tvExpandTouch: TextView by id(R.id.tv_view_delegate)
 *
 * tvExpandTouch.run {
 *     expandTouchView(50) //扩大点击区域
 *     setOnClickListener {
 *          showToast("通过TouchDelegate扩大点击区域")
 *     }
 * }
 * ```
 */
fun View.expandTouchView(expandSize: Int = dp2px(10f).toInt()) {
    val parentView = (parent as? View)
    parentView?.post {
        val rect = Rect()
        getHitRect(rect) //getHitRect(rect)将视图在父容器中所占据的区域存储到rect中。
        rect.left -= expandSize
        rect.top -= expandSize
        rect.right += expandSize
        rect.bottom += expandSize
        parentView.touchDelegate = TouchDelegate(rect, this)
    }
}