@file: JvmName("-time")

package com.edusoa.android.kotlin


import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 将格式化的时间字符串转换成Data
 * ```
 * val date = "2023-01-01".toDate(format = "yyyy-MM-dd")
 * ```
 */
fun String.toDate(format: String = "yyyy-MM-dd HH:mm:ss"): Date? {
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.parse(this)
}

/**
 * 将Date转换为字符串，有默认format
 * ```
 * val currentDate = Date().toStringFormat()
 * val currentDate2 = Date().toStringFormat(format = "dd-MM-yyyy")
 * ```
 */
fun Date.toStringFormat(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.format(this)
}