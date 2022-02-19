package com.github.markowanga.timberloggingtofile.crypt


interface TextCrypt {
    fun encryptText(text: String): String
    fun decryptText(text: String): String
}
