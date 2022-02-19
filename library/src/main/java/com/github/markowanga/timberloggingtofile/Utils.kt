package com.github.markowanga.timberloggingtofile

import android.util.Base64


fun ByteArray.toBase64(): String = Base64.encodeToString(this, Base64.DEFAULT)

fun String.toBytesFromBase64(): ByteArray =
    Base64.decode(this, Base64.DEFAULT)

