package com.github.markowanga.timberloggingtofile.logname

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ConstantDateTimeLogFileNameProvider(
    private val dateTime: LocalDateTime = LocalDateTime.now(),
    private val dateTimeFormatter: DateTimeFormatter = DEFAULT_FORMATTER,
    prefix: String = DEFAULT_PREFIX,
    extension: String = DEFAULT_EXTENSION
) : LogFileNameProvider(prefix, extension) {

    override fun getFileTag(dateTime: LocalDateTime) = dateTimeFormatter.format(this.dateTime)!!

    companion object {
        private val DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm")
    }

}
