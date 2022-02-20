package com.github.markowanga.timberloggingtofile

import android.content.Context
import timber.log.Timber

fun resetAll(context: Context) {
    Timber.uprootAll()
    LogManager.removeFilesOlderThanDays(0, LogManager.getExternalLogsDirectory(context))
    LogManager.removeFilesOlderThanDays(0, LogManager.getInternalLogsDirectory(context))
}