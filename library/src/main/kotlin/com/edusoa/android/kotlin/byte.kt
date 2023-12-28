@file: JvmName("-byte")

package com.edusoa.android.kotlin

import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
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


//gzip压缩
fun compressByGzip(data: ByteArray): ByteArray = ByteArrayOutputStream().apply {
    GZIPOutputStream(this).apply {
        write(data)
        finish()
    }
}.toByteArray()

//gzip压缩
fun ByteArray.gzipCompress(): ByteArray = compressByGzip(this)

//gzip解压缩字节流
fun uncompressByGzip(data: ByteArray): ByteArray =
    GZIPInputStream(data.inputStream()).use { it.readBytes() }

//gzip解压缩字节流
fun ByteArray.uncompressGzip(): ByteArray = uncompressByGzip(this)

/** 把字byte转换为十六进制的表现形式，如ff  */
fun byteToHex(byte: Byte) = String.format("%02x", byte.toInt() and 0xFF)

fun Byte.toHex():String = byteToHex(this)

fun ByteArray.toHex():String = this.joinToString { it.toHex() }