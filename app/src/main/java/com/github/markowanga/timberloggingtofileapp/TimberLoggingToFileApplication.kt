package com.github.markowanga.timberloggingtofileapp

import androidx.multidex.MultiDexApplication
import com.github.markowanga.timberloggingtofile.LogToFileFactory
import com.github.markowanga.timberloggingtofile.StorageProvider
import timber.log.Timber

class TimberLoggingToFileApplication : MultiDexApplication() {

    private val logToFileFactory by lazy { LogToFileFactory(this) }

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        logToFileFactory.run {
            listOf(
                Timber.DebugTree(),
                createPlainTextTree(),
                createBase64Tree(),
                createCipherTree()
            ).forEach { Timber.plant(it) }
        }
        Timber.i("Hello log ;) !!!")
    }
}
