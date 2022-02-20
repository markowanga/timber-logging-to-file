package com.github.markowanga.timberloggingtofile

import com.github.markowanga.timberloggingtofile.crypt.TextCrypt
import java.io.File
import java.time.format.DateTimeFormatter

class LogToFileFactory(
    private val logsDirectory: File,
) {
    fun create(
        textCrypt: TextCrypt? = null,
        logFilePrefix: String = DEFAULT_LOG_FILE_PREFIX,
        logFileExtension: String = DEFAULT_EXTENSION,
        dateTimeFormatter: DateTimeFormatter = DEFAULT_FORMATTER
    ) =
        LogToFileTimberTree(
            logsDirectory,
            textCrypt,
            logFilePrefix,
            logFileExtension,
            dateTimeFormatter
        )

    companion object {
        private const val DEFAULT_EXTENSION = ".log"
        private const val DEFAULT_LOG_FILE_PREFIX = "app_logs_"
        private val DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd")
    }
}