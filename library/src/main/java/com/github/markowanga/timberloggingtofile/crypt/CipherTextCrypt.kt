package com.github.markowanga.timberloggingtofile.crypt

import android.annotation.SuppressLint
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


class CipherTextCrypt(password: String) : TextCrypt {

    private val secretKey: SecretKey = generateKey(password)

    @SuppressLint("GetInstance")
    private val encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        .also { it.init(Cipher.ENCRYPT_MODE, secretKey) }

    @SuppressLint("GetInstance")
    private val decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        .also { it.init(Cipher.DECRYPT_MODE, secretKey) }

    private fun ByteArray.encryptByCipher() = encryptCipher.doFinal(this)

    private fun ByteArray.decryptByCipher() = decryptCipher.doFinal(this)

    override fun encryptText(text: String) =
        text.convertToBytes()
            .encryptByCipher()
            .encodeBase64()
            .convertToString()

    override fun decryptText(text: String) =
        text.convertToBytes()
            .decodeBase64()
            .decryptByCipher()
            .convertToString()

    companion object {
        private fun generateKey(password: String): SecretKey {
            return SecretKeySpec(password.toByteArray(), "AES")
        }
    }

}
