package xyz.junerver.kotlin

import android.os.Handler
import android.os.Looper
import java.util.Timer
import java.util.TimerTask

/**
 * Description:
 * @author Junerver
 * date: 2024/1/17-8:20
 * Email: junerver@gmail.com
 * Version: v1.0
 */

class Debounce(
    private val action: () -> Unit,
    private val delay: Long,
    private val onFailure: (Throwable) -> Unit
) : () -> Unit {
    private val timer = Timer()
    private var task: TimerTask? = null
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

fun (() -> Unit).debounce(
    delay: Long = 500,
    onFailure: (Throwable) -> Unit = errorLog
): Debounce {
    return Debounce(this, delay, onFailure)
}

class Debounce2(
    private val action: () -> Unit,
    private val delay: Long,
    private val onFailure: (Throwable) -> Unit
) : () -> Unit {
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
): Debounce2 {
    return Debounce2(this, delay, onFailure)
}