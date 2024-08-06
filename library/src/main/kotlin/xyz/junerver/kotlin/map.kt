@file: JvmName("-map")

package xyz.junerver.kotlin

/**
 * Description: 使用map实现一个copy覆盖算法，用来模拟js中的如下代码：
 * ```
 * const newObj = {
 *      ...old,
 *      ...other,
 *      ...another
 *  }
 * ```
 * @author Junerver
 * date: 2024/2/29-13:40
 * Email: junerver@gmail.com
 * Version: v1.0
 */
fun Map<String, Any?>.copy(vararg others: Map<String, Any?>): Map<String, Any?> {
    return listOf(this, *others).reduce { acc, map -> acc + map }
}