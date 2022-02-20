package com.github.markowanga.timberloggingtofileapp

import androidx.multidex.MultiDexApplication
import com.github.markowanga.timberloggingtofile.LogToFileTimberTree
import com.github.markowanga.timberloggingtofile.crypt.Base64TextCrypt
import com.github.markowanga.timberloggingtofile.crypt.CipherTextCrypt
import com.github.markowanga.timberloggingtofile.logname.DailyLogFileNameProvider
import com.github.markowanga.timberloggingtofile.storage.ExternalLogStorageProvider
import timber.log.Timber
import java.io.File

class TimberLoggingToFileApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        val storageProvider = ExternalLogStorageProvider(this)
        val rootLogDirectory = storageProvider.getStorageDirectory()
        getTrees(rootLogDirectory).forEach { Timber.plant(it) }
        Timber.i("Hello log ;) !!!")
    }

    private fun getTrees(rootLogDirectory: File) = listOf(
        Timber.DebugTree(),
        LogToFileTimberTree(rootLogDirectory, logFileNameProvider = DailyLogFileNameProvider()),
        LogToFileTimberTree(
            rootLogDirectory, Base64TextCrypt(),
            DailyLogFileNameProvider(prefix = "base64_")
        ),
        LogToFileTimberTree(
            rootLogDirectory,
            CipherTextCrypt(SECRET_PASSWORD),
            DailyLogFileNameProvider(prefix = "cipher_")
        ),
    )

    companion object {
        const val SECRET_PASSWORD = "test1234test1234"
    }

}
