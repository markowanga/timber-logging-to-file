package com.github.markowanga.timberloggingtofile

import com.github.markowanga.timberloggingtofile.crypt.TextCrypt
import timber.log.Timber
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

class LogToFileTimberTree(
    private val logsDirectory: File,
    private val textCrypt: TextCrypt? = null,
    private val logFilePrefix: String = DEFAULT_LOG_FILE_PREFIX,
    private val logFileExtension: String = DEFAULT_EXTENSION,
    private val dateTimeFormatter: DateTimeFormatter = DEFAULT_FORMATTER,
) : Timber.Tree() {

    private val priorityMap = mapOf(
        2 to "VERBOSE",
        3 to "DEBUG",
        4 to "INFO",
        5 to "WARN",
        6 to "ERROR",
        7 to "ASSERT",
    )

    private fun getLogFileNameFromDateTime(dateTime: LocalDate) =
        "$logFilePrefix${dateTimeFormatter.format(dateTime)}.$logFileExtension"

    private fun getLogFileNameForNow() = getLogFileNameFromDateTime(LocalDate.now())

    private fun saveLineToFile(line: String) {
        val lineToSave = textCrypt?.encryptText(line) ?: line
        File(logsDirectory, getLogFileNameForNow()).appendText("$lineToSave\n")
    }

    private fun createStackElementTag(element: StackTraceElement): String {
        var tag = element.className
        val m = Pattern.compile("(\\$\\d+)+$").matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        tag = tag.substring(tag.lastIndexOf('.') + 1)
        return if (tag.length <= MAX_TAG_LENGTH) {
            tag
        } else tag.substring(0, MAX_TAG_LENGTH)
    }

    private fun getFixedTag(tag: String?): String {
        if (tag != null) {
            return tag
        }
        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        val stackTrace = Throwable().stackTrace
        check(stackTrace.size > CALL_STACK_INDEX) {
            "Synthetic stacktrace didn't have enough elements: are you using proguard?"
        }
        return createStackElementTag(stackTrace[CALL_STACK_INDEX + 1])
    }

    private fun priorityToString(priority: Int) =
        priorityMap.getOrElse(priority) { throw IllegalAccessException() }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val time = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        val logValue = priorityToString(priority)
        val error = if (t == null) "" else "\n${t}\n${t.message}\n${t.stackTraceToString()}"
        saveLineToFile(
            "${time.padEnd(23)} ${logValue.padEnd(10)} ${
                "[" + (getFixedTag(tag)).padEnd(MAX_TAG_LENGTH) + "]"
            } $message $error"
        )
    }

    companion object {
        const val MAX_TAG_LENGTH = 33
        const val CALL_STACK_INDEX = 5

        private const val DEFAULT_EXTENSION = ".log"
        private const val DEFAULT_LOG_FILE_PREFIX = "app_logs_"
        private val DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd")
    }

}
