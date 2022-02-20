package com.github.markowanga.timberloggingtofile.logname

import java.time.LocalDateTime

abstract class LogFileNameProvider(
    private val prefix: String,
    private val extension: String
) {

    fun getFileName(dateTime: LocalDateTime) = "${prefix}${getFileTag(dateTime)}.${extension}"

    abstract fun getFileTag(dateTime: LocalDateTime): String

    companion object {
        const val DEFAULT_PREFIX = "app_logs_"
        const val DEFAULT_EXTENSION = "log"
    }

}
