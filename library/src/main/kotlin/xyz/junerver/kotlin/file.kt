@file: JvmName("-file")

package xyz.junerver.kotlin

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.reflect.KProperty

// 委托属性的委托类
class CachedMd5Delegate {
    private var cachedMd5: String? = null

    operator fun getValue(thisRef: File, property: KProperty<*>): String =
        cachedMd5 ?: computeMd5(thisRef).also { cachedMd5 = it }

    private fun computeMd5(file: File): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(file.readBytes())
        return BigInteger(1, digest).toString(16).padStart(32, '0')
    }
}

// 扩展属性
val File.md5: String by CachedMd5Delegate()

fun File.checksum(md5: String): Boolean = this.md5 == md5

infix fun File.are(that: File): Boolean = this.md5 == that.md5
