package com.edusoa.android.kotlin.lazy

import com.edusoa.android.kotlin.lazy.ManagedResettableLazy.defaultManager
import java.util.LinkedList
import kotlin.reflect.KProperty

/**
 * Description: 带管理者的resettableLazy函数，有默认管理器，也
 * 可以传入新的管理对象，可以实现批量管理
 * @author Junerver
 * date: 2023/10/31-10:15
 * Email: junerver@gmail.com
 * Version: v1.0
 */

class ResettableLazyManager {
    private val managedDelegates = LinkedList<Resettable>()

    internal fun register(managed: Resettable) {
        synchronized(managedDelegates) {
            managedDelegates.add(managed)
        }
    }

    fun reset() {
        synchronized(managedDelegates) {
            managedDelegates.forEach { it.reset() }
            managedDelegates.clear()
        }
    }
}

interface Resettable {
    fun reset()
}

class ResettableLazy<PROTOTYPE>(
    private val manager: ResettableLazyManager? = null,
    val init: () -> PROTOTYPE
) : Resettable {
    @Volatile
    var lazyHolder = makeInitBlock()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): PROTOTYPE {
        return lazyHolder.value
    }

    override fun reset() {
        lazyHolder = makeInitBlock()
    }

    private fun makeInitBlock(): Lazy<PROTOTYPE> {
        return lazy {
            (manager ?: defaultManager).register(this)
            init()
        }
    }
}

/**
 * 全局的默认管理器，当 [managedLazy] 函数不传递 `manager` 参数时，使用默认管理器属性，
 * 当需要重设默认管理器中的代理对象时，可以简单的使用 `ManagedResettableLazy.reset()` 来重设。
 */
object ManagedResettableLazy : Resettable {
    internal val defaultManager by lazy { resettableManager() }
    override fun reset() {
        defaultManager.reset()
    }
}

/**
 * 区别于 [resettableLazy] 函数，为了简短起名为此。
 * [manager] 默认值为 [ManagedResettableLazy] 单例的属性。
 */
fun <PROTOTYPE> managedLazy(
    manager: ResettableLazyManager = defaultManager,
    init: () -> PROTOTYPE
): ResettableLazy<PROTOTYPE> {
    return ResettableLazy(manager, init)
}

fun resettableManager(): ResettableLazyManager = ResettableLazyManager()