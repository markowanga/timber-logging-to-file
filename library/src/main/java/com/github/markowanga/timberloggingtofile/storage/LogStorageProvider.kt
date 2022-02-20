package com.github.markowanga.timberloggingtofile.storage

import java.io.File

interface LogStorageProvider {

    fun getStorageDirectory(logDirectoryName: String = DEFAULT_LOG_DIRECTORY_NAME): File

    fun File.prepareDirectory() =
        apply {
            if (!this.exists()) {
                this.mkdirs()
            }
        }

//    fun removeFilesOlderThanDays(daysCount: Long, rootFile: File) {
//        val minimumBefore = Instant.now().minus(daysCount, ChronoUnit.DAYS)
//        (rootFile.listFiles()?.toList() ?: listOf())
//            .filter { it.isFile }
//            .filter { Instant.ofEpochMilli(it.lastModified()).isBefore(minimumBefore) }
//            .forEach { it.delete() }
//    }

    companion object {
        const val DEFAULT_LOG_DIRECTORY_NAME = "app-logs"
    }
}
