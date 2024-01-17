package xyz.junerver.kotlin

/**
 * Description:
 * 函数节流，实现的效果是函数触发后立即执行[action]，然后记录执行时间，当下一次点击大于[delay]时才会再次执行[action]。
 * 这个函数具有泛用性，平台无关。
 * 不同于[setSingleClickListener]，这是节流而不是时限内只允许单次点击。
 * [setSingleClickListener]则更像前置执行[action]的特殊形式的防抖[debounce]。
 *
 * @author Junerver
 * date: 2024/1/17-16:13
 * Email: junerver@gmail.com
 * Version: v1.0
 */
class Throttle(
    private val action: () -> Unit,
    private val delay: Long,
    private val onFailure: (Throwable) -> Unit
) : () -> Unit {
    private var lastExecutionTime: Long = 0

    override fun invoke() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastExecutionTime >= delay) {
            runCatching(action).onFailure(onFailure)
            lastExecutionTime = currentTime
        }
    }
}

fun (() -> Unit).throttle(delay: Long = 500, onFailure: (Throwable) -> Unit = errorLog): Throttle {
    return Throttle(this, delay, onFailure)
}