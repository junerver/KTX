@file:OptIn(ExperimentalContracts::class)

@file: JvmName("-std")

package com.edusoa.android.kotlin

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


/**
 * 全局的空判断扩展属性，这样做空判断时更为优雅
 */
val Any?.isNull get() = this == null

val Any?.isNotNull get() = this != null

/**
 * 效果等同于 if(){}，条件达成执行闭包，它不同于if的是，它具有返回值，可以用于一些条件赋值、可空初始化，例如：
 * ```
 * var some:String? = null
 * if (isTrue){
 *   some = "xxxx"
 * }
 * ```
 * 这种初始化赋值可以被改写成：
 * ```
 * val some = runIf(isTrue){ "xxx" }
 * ```
 * 之所以写这个函数是因为 `if-else` 表达式支持赋值但是 `if` 不支持
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T, R> T.runIf(condition: Boolean = true, noinline block: T.() -> R): R? {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
        returnsNotNull() implies condition
    }
    return this.run { block.takeIf { condition }?.invoke(this) }
}

/**
 * 与上面的效果相反，条件达成则不执行，条件为否定时执行闭包
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T, R> T.runUnless(condition: Boolean = true, noinline block: T.() -> R): R? {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
        returnsNotNull() implies !condition
    }
    return this.run { block.takeUnless { condition }?.invoke(this) }
}

/**
 * 一个简单的if-else能返回结果的实现，类似于Either的左右值，它可以更好的将代码变成链式函数调用，
 * 也完全可以配合 arrow-kt，将结果封装成Either
 *```kotlin
 * //before
 * @JvmStatic
 * fun isFileExists(file: File?): Boolean {
 *     if (file == null) return false
 *     return if (file.exists()) true else isFileExists(file.absolutePath)
 * }
 * //after
 * fun isFileExists(file: File?): Boolean {
 *     return file?.exists()?.switches(
 *         ifTrue = { it },
 *         ifFalse = { isFileExists(file.absolutePath) }
 *     ) ?: false
 * }
 * ```
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T> Boolean.switches(
    noinline ifTrue: (Boolean) -> T,
    noinline ifFalse: (Boolean) -> T
): T {
    contract {
        callsInPlace(ifTrue, InvocationKind.AT_MOST_ONCE)
        callsInPlace(ifFalse, InvocationKind.AT_MOST_ONCE)
    }
    return if (this) ifTrue(this) else ifFalse(this)
}

