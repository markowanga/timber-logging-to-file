package com.github.markowanga.timberloggingtofile.crypt

import org.apache.commons.codec.binary.Base64

fun String.convertToBytes() = toByteArray(Charsets.UTF_8)

fun ByteArray.convertToString() = String(this, Charsets.UTF_8)

fun ByteArray.encodeBase64() = Base64.encodeBase64(this)!!

fun ByteArray.decodeBase64() = Base64.decodeBase64(this)!!
