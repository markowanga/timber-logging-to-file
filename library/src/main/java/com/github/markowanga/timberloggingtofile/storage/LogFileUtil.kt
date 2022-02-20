package com.github.markowanga.timberloggingtofile.storage

import java.io.File
import java.time.Instant
import java.time.temporal.ChronoUnit


object LogFileUtil {

    fun removeFilesOlderThanDays(daysCount: Long, storageDirectory: File) {
        val minimumBefore = Instant.now().minus(daysCount, ChronoUnit.DAYS)
        (storageDirectory.listFiles()?.toList() ?: listOf())
            .filter { it.isFile }
            .filter { Instant.ofEpochMilli(it.lastModified()).isBefore(minimumBefore) }
            .forEach { it.delete() }
    }

}
