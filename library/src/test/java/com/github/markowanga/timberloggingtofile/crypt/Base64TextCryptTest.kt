package com.github.markowanga.timberloggingtofile.crypt

import org.junit.Assert
import org.junit.Test

class Base64TextCryptTest {

    @Test
    fun encryptText() {
        val crypted = getCipherTextCrypt().encryptText(MESSAGE)
        Assert.assertEquals(CRYPTED_MESSAGE, crypted)
    }

    @Test
    fun decryptText() {
        val crypted = getCipherTextCrypt().decryptText(CRYPTED_MESSAGE)
        Assert.assertEquals(MESSAGE, crypted)
    }

    @Test
    fun fullProcess() {
        val crypted = getCipherTextCrypt().encryptText(MESSAGE)
        val message = getCipherTextCrypt().decryptText(crypted)
        Assert.assertEquals(MESSAGE, message)
    }

    private fun getCipherTextCrypt() = Base64TextCrypt()


    companion object {
        const val MESSAGE = "hello"
        const val CRYPTED_MESSAGE = "aGVsbG8="
    }

}
