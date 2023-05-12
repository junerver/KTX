package com.edusoa.android.kotlin


import android.view.View


//region convert扩展
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
//endregion



const val TAG = "Extensions"


//region view扩展
inline fun View.setSingleClickListener(crossinline block: (View) -> Unit) {
    var lastTime = 0L
    setOnClickListener {
        try {
            if (System.currentTimeMillis() - lastTime < 1500) return@setOnClickListener
            block(it)
        } finally {
            lastTime = System.currentTimeMillis()
        }
    }
}

fun View.gone() {
    this.visibility = View.GONE
}

fun gones(vararg views: View?) {
    views.forEach {
        it?.gone()
    }
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun invisibles(vararg views: View) {
    views.forEach {
        it.invisible()
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun visibles(vararg views: View) {
    views.forEach {
        it.visible()
    }
}
//endregion



