package xyz.junerver.kotlin

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.util.Timer
import java.util.TimerTask

/**
 * Description:
 * 防抖动函数，点击后不会立即执行函数，而是在delay延时完毕后才执行，如果在此间再次触发，会清空计时任务。
 * 生活化例如：电梯，触发关门函数，但是这期间再次触发就会再次等待delay时长。
 * @author Junerver
 * date: 2024/1/17-8:20
 * Email: junerver@gmail.com
 * Version: v1.0
 */

internal open class Debounce(
    private val action: () -> Unit,
    private val delay: Long,
    private val onFailure: (Throwable) -> Unit
) : () -> Unit {
    protected val timer = Timer()
    protected var task: TimerTask? = null
    override fun invoke() {
        task?.cancel()
        task = object : TimerTask() {
            override fun run() {
                runCatching(action).onFailure(onFailure)
            }
        }
        timer.schedule(task, delay)
    }
}

internal class DebounceWithLifecycle(
    action: () -> Unit,
    delay: Long,
    onFailure: (Throwable) -> Unit,
    lifecycleOwner: LifecycleOwner?
) : Debounce(action, delay, onFailure) {
    init {
        lifecycleOwner?.lifecycle?.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                runIf(event == Lifecycle.Event.ON_DESTROY) {
                    task?.cancel()
                    timer.cancel()
                    Log.d(TAG, "onStateChanged: act destroy ,task cancel")
                }
            }
        })
    }
}

fun (() -> Unit).debounce(
    delay: Long = 500,
    onFailure: (Throwable) -> Unit = errorLog,
    lifecycleOwner: LifecycleOwner? = null
): () -> Unit {
    return DebounceWithLifecycle(this, delay, onFailure, lifecycleOwner)
}


internal class Debounce2(
    private val action: () -> Unit,
    private val delay: Long,
    private val onFailure: (Throwable) -> Unit
) : Debounce(action, delay, onFailure) {
    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    override fun invoke() {
        runnable?.let { handler.removeCallbacks(it) }
        runnable = Runnable {
            runCatching(action).onFailure(onFailure)
        }
        handler.postDelayed(runnable!!, delay)
    }
}

fun (() -> Unit).debounce2(
    delay: Long = 500,
    onFailure: (exception: Throwable) -> Unit = errorLog
): () -> Unit {
    return Debounce2(this, delay, onFailure)
}