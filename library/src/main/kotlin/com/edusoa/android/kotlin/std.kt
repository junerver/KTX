package com.edusoa.android.kotlin

/**
 * Description:
 * @author Junerver
 * date: 2023/5/26-11:23
 * Email: junerver@gmail.com
 * Version: v1.0
 */
fun <T, R> T.runIf(condition: Boolean = true, block: T.() -> R) = run {
    block.takeIf { condition }?.invoke(this)
}