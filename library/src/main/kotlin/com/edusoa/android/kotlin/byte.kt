package com.edusoa.android.kotlin

import java.nio.ByteBuffer
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

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
@OptIn(ExperimentalEncodingApi::class)
fun ByteArray.toBase64(): String = Base64.encode(this)

@OptIn(ExperimentalEncodingApi::class)
fun ByteArray.decodeBase64(): ByteArray = Base64.decode(this)