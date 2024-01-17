package xyz.junerver.kotlin

import android.util.Log

/**
 * Description:
 * @author Junerver
 * date: 2024/1/17-9:02
 * Email: junerver@gmail.com
 * Version: v1.0
 */
val errorLog: ((Throwable) -> Unit) = {
    Log.e("ktx", "catch error: ${it.message}\n"+ it.stackTraceToString() )
}