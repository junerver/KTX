package xyz.junerver.android.edusoaktx

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import xyz.junerver.android.kotlin.toBase64

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("xyz.junerver.android.edusoaktx", appContext.packageName)

    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun testBase64() {
        val str = "这是测试文本"
        val a64 = str.toBase64()
        val k64 = Base64.encode(str.toByteArray())
        assertEquals(a64,k64)
    }
}