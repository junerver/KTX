@file: JvmName("-list")

package xyz.junerver.kotlin

/*
  Description: 
  Author: Junerver
  Date: 2024/8/6-10:30
  Email: junerver@gmail.com
  Version: v1.0
*/

/**
 * 使用一个新的元素替换符合条件的第一个元素
 *
 * @param element
 * @param predicate
 * @param T
 * @return 返回旧元素，如果存在的话
 * @receiver
 */
public inline fun <T> MutableList<T>.replaceWith(element: T, predicate: (T) -> Boolean): T? {
    var old: T? = null
    this.indexOfFirst(predicate)
        .takeIf { it != -1 }
        ?.let { index ->
            old = this[index]
            this[index] = element
        }
    return old
}