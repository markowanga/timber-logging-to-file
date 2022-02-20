package com.github.markowanga.timberloggingtofile.storage

import android.content.Context
import androidx.core.content.ContextCompat
import java.io.File

class ExternalLogStorageProvider(
    private val context: Context
) : LogStorageProvider {

    override fun getStorageDirectory(logDirectoryName: String) =
        File(getPrimaryExternalStorage(), logDirectoryName)
            .prepareDirectory()

    private fun getPrimaryExternalStorage(): File {
        val externalStorageVolumes: Array<out File> =
            ContextCompat.getExternalFilesDirs(context, null)
        return externalStorageVolumes[0]
    }

}
