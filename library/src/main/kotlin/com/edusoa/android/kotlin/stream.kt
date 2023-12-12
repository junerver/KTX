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
 * 由于一般的读写操作都是比较模板的代码，直接用这个中缀表达式更方便
 */
@Throws(IOException::class)
internal infix fun InputStream.writeTo(outputStream: OutputStream) {
    outputStream.use { out ->
        this.use { input ->
            val buffer = ByteArray(1024)
            var length: Int
            while (input.read(buffer).also { length = it } > 0) {
                out.write(buffer, 0, length)
            }
            out.flush()
        }
    }
}