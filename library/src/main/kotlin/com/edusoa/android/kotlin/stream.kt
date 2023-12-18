@file: JvmName("-stream")

package com.edusoa.android.kotlin

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * Description:
 * @author Junerver
 * date: 2023/11/16-8:33
 * Email: junerver@gmail.com
 * Version: v1.0
 */

/**
 * 由于一般的读写操作都是比较模板的代码，直接用这个中缀表达式更方便.
 * 修改成调用官方实现[copyTo]，只是额外关闭了输入输出流，算是对官方实现的一个补充
 */
@Throws(IOException::class)
internal infix fun InputStream.writeTo(outputStream: OutputStream): Long = outputStream.use { output ->
    this.use { input ->
        input.copyTo(output)
    }.also { output.flush() }
}

/**
 * 方便的从流中读取指定长度的字节数组
 */
@Throws(IOException::class)
fun InputStream.read(length: Int): ByteArray {
    var readBytes = 0
    val buffer = ByteArray(length)
    while (readBytes < length) {
        val read = this.read(buffer, readBytes, length - readBytes)
        if (read == -1) throw IOException("inputStream read -1")
        readBytes += read
    }
    return buffer
}
