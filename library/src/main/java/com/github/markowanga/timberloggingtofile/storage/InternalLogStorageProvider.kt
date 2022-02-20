package com.github.markowanga.timberloggingtofile.storage

import android.content.Context
import java.io.File

class InternalLogStorageProvider(
    private val context: Context
) : LogStorageProvider {

    override fun getStorageDirectory(logDirectoryName: String) =
        File(context.filesDir, logDirectoryName)
            .prepareDirectory()

}
