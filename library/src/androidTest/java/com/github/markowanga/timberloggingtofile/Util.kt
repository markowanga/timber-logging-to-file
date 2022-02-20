package com.github.markowanga.timberloggingtofile

import android.content.Context
import com.github.markowanga.timberloggingtofile.storage.ExternalLogStorageProvider
import com.github.markowanga.timberloggingtofile.storage.InternalLogStorageProvider
import com.github.markowanga.timberloggingtofile.storage.LogFileUtil
import timber.log.Timber

fun resetAll(context: Context) {
    Timber.uprootAll()
    listOf(InternalLogStorageProvider(context), ExternalLogStorageProvider(context))
        .forEach {
            LogFileUtil.removeFilesOlderThanDays(0, it.getStorageDirectory())
        }
}
