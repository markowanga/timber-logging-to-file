package com.github.markowanga.timberloggingtofile.crypt

import org.junit.Assert.assertEquals
import org.junit.Test

class CipherTextCryptTest {

    @Test
    fun encryptText() {
        val crypted = getCipherTextCrypt().encryptText(MESSAGE)
        assertEquals(CRYPTED_MESSAGE, crypted)
    }

    @Test
    fun decryptText() {
        val crypted = getCipherTextCrypt().decryptText(CRYPTED_MESSAGE)
        assertEquals(MESSAGE, crypted)
    }

    @Test
    fun fullProcess() {
        val crypted = getCipherTextCrypt().encryptText(MESSAGE)
        val message = getCipherTextCrypt().decryptText(crypted)
        assertEquals(MESSAGE, message)
    }

    private fun getCipherTextCrypt() = CipherTextCrypt(PASSWORD)


    companion object {
        const val PASSWORD = "test1234test1234"
        const val MESSAGE = "hello"
        const val CRYPTED_MESSAGE = "BkuzaQZF07uxmJpZWebc7g=="
    }

}
