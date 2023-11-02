@file: JvmName("-resettableLazy")

package com.edusoa.android.kotlin.lazy

/**
 * Description: 一个简单实现的`resettableLazy`
 * @author Junerver
 * date: 2023/10/31-10:15
 * Email: junerver@gmail.com
 * Version: v1.0
 */
import java.util.concurrent.atomic.AtomicReference
import kotlin.reflect.KProperty

/**
 * Sample:
 * ```kotlin
 *  val resettableDelegate = resettableLazy {
 *      Event(1,"2"+ Random(1))
 *  }
 *  val readOnly by resettableDelegate
 *  resettableDelegate.reset()
 * ```
 */
fun <T> resettableLazy(initializer: () -> T) = ResettableDelegate(initializer)

class ResettableDelegate<T>(private val initializer: () -> T) {
    private val lazyRef: AtomicReference<Lazy<T>> = AtomicReference(
        lazy(
            initializer
        )
    )

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return lazyRef.get().getValue(thisRef, property)
    }

    fun reset() {
        lazyRef.set(lazy(initializer))
    }
}
