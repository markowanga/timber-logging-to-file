package com.github.markowanga.timberloggingtofile.crypt

import org.junit.Assert.assertEquals
import org.junit.Test

class CipherTextCryptTest {

    @Test
    fun encryptMsg() {
        val crypted = getCipherTextCrypt().encryptText(MESSAGE)
        assertEquals("BkuzaQZF07uxmJpZWebc7g==", crypted)
    }

    @Test
    fun decryptMsg() {
        val crypted = getCipherTextCrypt().decryptText("BkuzaQZF07uxmJpZWebc7g==")
        assertEquals(MESSAGE, crypted)
    }

    @Test
    fun fullTest() {
        val crypted = getCipherTextCrypt().encryptText(MESSAGE)
        val message = getCipherTextCrypt().decryptText(crypted)
        assertEquals(MESSAGE, message)
    }

    private fun getCipherTextCrypt() = CipherTextCrypt(PASSWORD)

    companion object {
        const val PASSWORD = "test1234test1234"
        const val MESSAGE = "hello"
    }

}