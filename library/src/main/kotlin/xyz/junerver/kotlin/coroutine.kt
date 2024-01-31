package xyz.junerver.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/**
 * Description:
 * @author Junerver
 * date: 2024/1/31-11:16
 * Email: junerver@gmail.com
 * Version: v1.0
 */
/**
 * 一个可取消的重复间隔执行方法
 */
fun CoroutineScope.launchPeriodicAsync(
    repeatMillis: Long,
    action: () -> Unit
) = this.async {
    if (repeatMillis > 0) {
        while (isActive) {
            action()
            delay(repeatMillis)
        }
    } else {
        action()
    }
}