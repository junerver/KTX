package xyz.junerver.kotlin

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Description:
 * @author Junerver
 * date: 2024/2/29-13:46
 * Email: junerver@gmail.com
 * Version: v1.0
 */
@RunWith(RobolectricTestRunner::class)
class TestMap {
    @Test
    fun testCopy() {
        val old = mapOf(
            "a" to "aa",
            "b" to null,
            "c" to "cc"
        )
        val other = mapOf(
            "a" to null,
            "b" to "b",
            "d" to "d"
        )
        val r = old + other
        val my = old.copy(other)

        println(old)
        println(other)
        println(r)
        assertEquals(r,my)
    }
}