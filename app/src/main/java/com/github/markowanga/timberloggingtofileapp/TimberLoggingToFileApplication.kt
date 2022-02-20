package com.github.markowanga.timberloggingtofileapp

import androidx.multidex.MultiDexApplication
import com.github.markowanga.timberloggingtofile.LogToFileFactory
import com.github.markowanga.timberloggingtofile.StorageProvider
import com.github.markowanga.timberloggingtofile.crypt.Base64TextCrypt
import com.github.markowanga.timberloggingtofile.crypt.CipherTextCrypt
import timber.log.Timber

class TimberLoggingToFileApplication : MultiDexApplication() {

    private val storageProvider by lazy { StorageProvider(this) }
    private val logToFileFactory by lazy {
        LogToFileFactory(storageProvider.getExternalLogsDirectory())
    }

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

    private fun getPlainTextTree() = logToFileFactory.create(
        logFilePrefix = "plain_text_"
    )

    private fun getBase64Tree() = logToFileFactory.create(
        Base64TextCrypt(),
        logFilePrefix = "base64_"
    )

    private fun getCipherTree() = logToFileFactory.create(
        CipherTextCrypt("test1234test1234"),
        logFilePrefix = "cipher_"
    )
}
