package com.github.markowanga.timberloggingtofile

import android.annotation.SuppressLint
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

fun ByteArray.toBase64(): String = Base64.encodeToString(this, Base64.DEFAULT)

fun String.toBytesFromBase64(): ByteArray =
    Base64.decode(this, Base64.DEFAULT)


class TextCrypt(private val password: String) {

    private val secretKey: SecretKey = generateKey()

    @SuppressLint("GetInstance")
    private val encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        .also { it.init(Cipher.ENCRYPT_MODE, secretKey) }

    @SuppressLint("GetInstance")
    private val decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        .also { it.init(Cipher.DECRYPT_MODE, secretKey) }

    private fun generateKey(): SecretKey {
        return SecretKeySpec(password.toByteArray(), "AES")
    }

    fun encryptMsg(message: String): String =
        encryptCipher.doFinal(message.toByteArray(charset("UTF-8"))).toBase64()

    fun decryptMsg(cipherText: String) =
        String(decryptCipher.doFinal(cipherText.toBytesFromBase64()), Charsets.UTF_8)

}
