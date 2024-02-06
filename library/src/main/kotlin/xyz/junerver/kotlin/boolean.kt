package xyz.junerver.kotlin

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Description: 针对布尔值的相关扩展拆分
 * @author Junerver
 * date: 2024/2/5-9:17
 * Email: junerver@gmail.com
 * Version: v1.0
 */

/**
 * 将任意值转换成布尔，类似JS的 Boolean包装器函数
 */
fun toBoolean(value: Any?): Boolean {
    return when (value) {
        null -> false
        is Boolean -> value
        is Number -> value != 0
        is String -> value.isNotEmpty() && value != "false"
        else -> true
    }
}

val Any?.asBoolean: Boolean
    get() = toBoolean(this)


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
    return if (this) ifTrue(true) else ifFalse(false)
}

/**
 * 可空布尔量的字面值，如果他是空则取默认值
 */
fun Boolean?.asIs(default: Boolean = true): Boolean = this ?: default

@Deprecated("Boolean has fun name 'not', so change fun name", ReplaceWith("this.asNot(default)"))
fun Boolean?.not(default: Boolean = false): Boolean = this.asNot(default)

/**
 * 可空布尔量字面值取反，如果为空则取默认值
 */
fun Boolean?.asNot(default: Boolean = false): Boolean = this?.run { !this } ?: default