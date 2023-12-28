@file: JvmName("-encrypt")
@file:OptIn(ExperimentalContracts::class)

package com.edusoa.android.kotlin

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import kotlin.contracts.ExperimentalContracts

/**
 * Description:
 * @author Junerver
 * date: 2023/11/15-9:31
 * Email: junerver@gmail.com
 * Version: v1.0
 */
object EncryptUtils {
    /**
     * base64编码的公钥字符串，注意不要包含协议头
     */
    var publicKey: String? = null

    /**
     * base64编码的私钥字符串，注意不要包含协议头，不可以使用加密私钥
     */
    var privateKey: String? = null


    internal val _publicKey: PublicKey
        get() {
            require(publicKey.isNotNullOrEmpty()) { "must init public key with base64 key" }
            return KeyFactory.getInstance("RSA")
                .generatePublic(X509EncodedKeySpec(publicKey!!.base64ToByteArray()))
        }

    internal val _privateKey: PrivateKey
        get() {
            require(privateKey.isNotNullOrEmpty()) { "must init private key with base64 key" }
            return KeyFactory.getInstance("RSA")
                .generatePrivate(PKCS8EncodedKeySpec(privateKey!!.base64ToByteArray()))
        }
}

fun String.encryptRsa(): String =
    Cipher.getInstance("RSA/ECB/PKCS1Padding").apply {
        init(
            Cipher.ENCRYPT_MODE,
            EncryptUtils._publicKey
        )
    }
        .doFinal(this.toByteArray()).toBase64()

fun String.decryptRsa(): String? {
    val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        .apply { init(Cipher.DECRYPT_MODE, EncryptUtils._privateKey) }
    return runCatching {
        ByteArrayInputStream(this.base64ToByteArray()).use { bis ->
            ByteArrayOutputStream().use { bos ->
                val buf = ByteArray(128)
                var bufl: Int
                while (bis.read(buf).also { bufl = it } != -1) {
                    var block: ByteArray?
                    if (buf.size == bufl) {
                        block = buf
                    } else {
                        block = ByteArray(bufl)
                        for (i in 0 until bufl) {
                            block[i] = buf[i]
                        }
                    }
                    bos.write(cipher.doFinal(block))
                }
                String(bos.toByteArray(), StandardCharsets.UTF_8)
            }
        }
    }.getOrNull()
}


