package com.github.markowanga.timberloggingtofile.crypt


class Base64TextCrypt : TextCrypt {

    override fun encryptText(text: String) =
        text.convertToBytes()
            .encodeBase64()
            .convertToString()

    override fun decryptText(text: String) =
        text.convertToBytes()
            .decodeBase64()
            .convertToString()

}
