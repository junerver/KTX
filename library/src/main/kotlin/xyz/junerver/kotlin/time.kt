@file: JvmName("-time")

package xyz.junerver.kotlin


import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 将格式化的时间字符串转换成Data
 *
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
 *
 * ```
 * val currentDate = Date().toStringFormat()
 * val currentDate2 = Date().toStringFormat(format = "dd-MM-yyyy")
 * ```
 */
fun Date.toStringFormat(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.format(this)
}

/**
 * Timestamp
 *
 * @constructor Create empty Timestamp
 * @property value
 */
@JvmInline
value class Timestamp(val value: Long) : Comparable<Timestamp> {
    override fun compareTo(other: Timestamp): Int = value.compareTo(other.value)

    /** Add two [Timestamp]s together. */
    inline operator fun plus(other: Timestamp) =
        Timestamp(value = this.value + other.value)

    /** Subtract a Timestamp from another one. */
    inline operator fun minus(other: Timestamp) =
        Timestamp(value = this.value - other.value)

    inline val asTimestampSeconds: Long get() = value / 1000

    inline val asTimestampMilliseconds: Long get() = value

    companion object {
        /**
         * Create a Timestamp
         *
         * @return
         */
        fun now(): Timestamp = Clock.System.now().toEpochMilliseconds().tsMs
    }
}

inline val Long.tsMs: Timestamp get() = Timestamp(value = this)

inline val Long.tsS: Timestamp get() = Timestamp(value = this * 1000)

public fun Timestamp.toLocalDateTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime =
    Instant.fromEpochMilliseconds(this.value).toLocalDateTime(timeZone)

public val DayOfWeekNames.Companion.CHINESE_FULL: DayOfWeekNames
    get() = DayOfWeekNames("星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日")

public val DayOfWeekNames.Companion.CHINESE_ABBREVIATED: DayOfWeekNames
    get() = DayOfWeekNames("周一", "周二", "周三", "周四", "周五", "周六", "周日")
