package com.github.markowanga.timberloggingtofile

import android.content.Context
import androidx.core.content.ContextCompat
import java.io.File
import java.time.Instant
import java.time.temporal.ChronoUnit

object LogManager {

    private const val DEFAULT_LOG_DIRECTORY = "app-logs"

    private fun getPrimaryExternalStorage(context: Context): File {
        val externalStorageVolumes: Array<out File> =
            ContextCompat.getExternalFilesDirs(context, null)
        return externalStorageVolumes[0]
    }

    fun getExternalLogsDirectory(
        context: Context,
        logDirectory: String = DEFAULT_LOG_DIRECTORY
    ): File =
        File(getPrimaryExternalStorage(context), logDirectory).apply {
            if (!this.exists()) {
                this.mkdirs()
            }
        }

    fun removeFilesOlderThanDays(daysCount: Long, rootFile: File) {
        val minimumBefore = Instant.now().minus(daysCount, ChronoUnit.DAYS)
        (rootFile.listFiles()?.toList() ?: listOf())
            .filter { it.isFile }
            .filter { Instant.ofEpochMilli(it.lastModified()).isBefore(minimumBefore) }
            .forEach { it.delete() }
    }

}
