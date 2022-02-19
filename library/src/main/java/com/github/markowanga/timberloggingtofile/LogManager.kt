package com.github.markowanga.timberloggingtofile

import android.content.Context
import androidx.core.content.ContextCompat
import java.io.File

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

}
