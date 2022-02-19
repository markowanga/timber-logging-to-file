package com.github.markowanga.timberloggingtofile

import android.annotation.SuppressLint
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


class TextCrypt(password: String) {

    private val secretKey: SecretKey = generateKey(password)

    @SuppressLint("GetInstance")
    private val encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        .also { it.init(Cipher.ENCRYPT_MODE, secretKey) }

    @SuppressLint("GetInstance")
    private val decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        .also { it.init(Cipher.DECRYPT_MODE, secretKey) }

    private fun generateKey(password: String): SecretKey {
        return SecretKeySpec(password.toByteArray(), "AES")
    }

    fun encryptMsg(message: String): String =
        encryptCipher.doFinal(message.toByteArray(charset("UTF-8"))).toBase64()

    fun decryptMsg(cipherText: String) =
        String(decryptCipher.doFinal(cipherText.toBytesFromBase64()), Charsets.UTF_8)

}
