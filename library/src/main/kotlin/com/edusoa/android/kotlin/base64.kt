package com.edusoa.android.kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * Description:
 * @author Junerver
 * date: 2023/12/25-9:38
 * Email: junerver@gmail.com
 * Version: v1.0
 */
//原始字节数组编码为base64字符串
@OptIn(ExperimentalEncodingApi::class)
fun ByteArray.toBase64(): String = Base64.encode(this)

//base64 编码的
@OptIn(ExperimentalEncodingApi::class)
fun ByteArray.decodeBase64(): ByteArray = Base64.decode(this)

//字符串直接转换为Base64
fun String.toBase64(): String = this.toByteArray().toBase64()

/**
 * base64字符串直接解码为普通字符串，与函数[toBase64]互为逆操作
 */
fun String.decodeBase64(): String = this.base64ToByteArray().decodeToString()

fun ByteArrayOutputStream.toBase64():String = this.toByteArray().toBase64()

//base64字符串，解码成原始字节数组
@OptIn(ExperimentalEncodingApi::class)
fun String.base64ToByteArray(): ByteArray = Base64.decode(this)

// 将bitmap编码为base64字符串
fun Bitmap.toBase64(): String {
    this.takeUnless { it.isRecycled } ?: return ""
    val baos = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val photoString = baos.toBase64()
    return photoString.also { recycle() }
}

//base64编码图片解码
fun String.base64ToBitmap(): Bitmap {
    val bytes = this.base64ToByteArray()
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

