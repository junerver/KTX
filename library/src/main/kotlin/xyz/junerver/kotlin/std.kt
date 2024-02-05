@file:OptIn(ExperimentalContracts::class)

@file: JvmName("-std")

package xyz.junerver.kotlin

import java.io.Serializable
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.typeOf

const val TAG = "Junerver - KTX"

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
 * 一个返回Unit的扩展函数，只保障闭包执行，可以看成是无返回值的 [let] 函数，
 * 在一些需要函数返回值的场景可以用来替代显式书写的[Unit]。
 */
public inline fun <T> T.then(block: (T) -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block(this)
    return
}

/**
 * 打印泛型
 */
inline fun <reified T> printType(@Suppress("UNUSED_PARAMETER") t: T) {
    val type = typeOf<T>()
    println(type)
}

/**
 * 一个kotlin实现的类似三元表达式，甚至由于语言的特性，可以用来返回函数：
 * ```kotlin
 * val r = true `？` { "r:true" } `：` { "r:false" }
 * val r1 = false `？` "" `：` "r:false"
 * ```
 */
data class WrapBoolean<R>(val condition: Boolean, val result: R?)

inline infix fun <R> Boolean.`？`(ifTrue: R?): WrapBoolean<R> {
    val result = if (this) {
        ifTrue
    } else {
        null
    }
    return WrapBoolean(this, result)
}

inline infix fun <R> WrapBoolean<R>.`：`(ifFalse: R?): R? {
    return if (!this.condition) {
        ifFalse
    } else {
        this.result
    }
}


/**
 * 四个数据的元组类型
 */
data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
) : Serializable {
    public override fun toString(): String = "($first, $second, $third, $fourth)"
}

public fun <T> Quadruple<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)

/**
 * 元组扩展，可以使用 `a to b to c` 这样的连续中缀函数创建更多元素的元组
 */
infix fun <A, B, C> Pair<A, B>.to(c: C): Triple<A, B, C> = Triple(this.first, this.second, c)
infix fun <A, B, C, D> Triple<A, B, C>.to(d: D): Quadruple<A, B, C, D> =
    Quadruple(this.first, this.second, this.third, d)

