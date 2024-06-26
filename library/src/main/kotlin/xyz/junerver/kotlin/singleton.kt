@file: JvmName("-singleton")

package xyz.junerver.kotlin

/**
 * 单参数单例，使用方法：
 * ```kotlin
 * class WorkSingleton private constructor(context: Context) {
 *
 *     companion object : SingletonHolder<WorkSingleton, Context>(::WorkSingleton)
 *
 *     init {
 *         // Init using context argument
 *     }
 * }
 * ```
 */
open class SingletonHolder<out T, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator

    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T =
        instance ?: synchronized(this) {
            instance ?: creator!!(arg).apply {
                instance = this
            }
        }
}
