package com.github.markowanga.timberloggingtofileapp

import androidx.multidex.MultiDexApplication
import com.github.markowanga.timberloggingtofile.LogManager
import com.github.markowanga.timberloggingtofile.LogToFileTimberTree
import com.github.markowanga.timberloggingtofile.crypt.Base64TextCrypt
import com.github.markowanga.timberloggingtofile.crypt.CipherTextCrypt
import timber.log.Timber


class TimberLoggingToFileApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        listOf(
            Timber.DebugTree(),
            getPlainTextTree(),
            getBase64Tree(),
            getCipherTree()
        ).forEach { Timber.plant(it) }
        Timber.i("Hello log ;) !!!")
    }

    private fun getPlainTextTree() = LogToFileTimberTree(
        LogManager.getExternalLogsDirectory(this),
        logFilePrefix = "plain_text_"
    )

    private fun getBase64Tree() = LogToFileTimberTree(
        LogManager.getExternalLogsDirectory(this),
        Base64TextCrypt(),
        logFilePrefix = "base64_"
    )

    private fun getCipherTree() = LogToFileTimberTree(
        LogManager.getExternalLogsDirectory(this),
        CipherTextCrypt("test1234test1234"),
        logFilePrefix = "cipher_"
    )

}
