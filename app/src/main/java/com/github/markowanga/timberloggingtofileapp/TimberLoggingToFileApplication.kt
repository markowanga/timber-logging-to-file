package com.github.markowanga.timberloggingtofileapp

import androidx.multidex.MultiDexApplication
import com.github.markowanga.timberloggingtofile.LogManager
import com.github.markowanga.timberloggingtofile.LogToFileTimberTree
import timber.log.Timber


class TimberLoggingToFileApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        Timber.plant(Timber.DebugTree())
        Timber.plant(
            LogToFileTimberTree(LogManager.getExternalLogsDirectory(this))
        )
        Timber.i("Hello log ;) !!!")
    }

}
