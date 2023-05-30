package com.edusoa.android.kotlin

import android.util.Base64
import java.nio.ByteBuffer

/**
 * Description:
 * @author Junerver
 * date: 2023/5/12-13:56
 * Email: junerver@gmail.com
 * Version: v1.0
 */
//bf转ba
fun bytebuffer2ByteArray(buffer: ByteBuffer): ByteArray =
    ByteArray(buffer.capacity()).apply { buffer.get(this) }

//bf转ba
fun ByteBuffer.toByteArray(): ByteArray = bytebuffer2ByteArray(this)

//ba转base64
fun ByteArray.toBase64(): String = Base64.encodeToString(this, Base64.NO_WRAP)
