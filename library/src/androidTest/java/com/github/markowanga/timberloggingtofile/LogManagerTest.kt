package com.github.markowanga.timberloggingtofile

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.markowanga.timberloggingtofile.crypt.Base64TextCrypt
import com.github.markowanga.timberloggingtofile.storage.ExternalLogStorageProvider
import com.github.markowanga.timberloggingtofile.storage.LogFileUtil
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber

@RunWith(AndroidJUnit4::class)
class LogManagerTest {

    @Before
    @After
    fun resetAll() {
        resetAll(getContext())
    }

    @Test
    fun removeFilesOlderThan2Days() {
        val storageProvider = ExternalLogStorageProvider(getContext())
        val rootDirectory = storageProvider.getStorageDirectory()
        Timber.plant(LogToFileTimberTree(rootDirectory))
        Timber.plant(
            LogToFileTimberTree(rootDirectory, Base64TextCrypt(), logFilePrefix = "base64_")
        )
        Timber.i("hello log")
        LogFileUtil.removeFilesOlderThanDays(2, rootDirectory)
        assertEquals(2, rootDirectory.list()?.size ?: 0)
    }

    @Test
    fun removeFilesOlderThan0Days() {
        val storageProvider = ExternalLogStorageProvider(getContext())
        val rootDirectory = storageProvider.getStorageDirectory()
        Timber.plant(LogToFileTimberTree(rootDirectory))
        Timber.plant(
            LogToFileTimberTree(rootDirectory, Base64TextCrypt(), logFilePrefix = "base64_")
        )
        Timber.i("hello log")
        LogFileUtil.removeFilesOlderThanDays(0, rootDirectory)
        assertEquals(0, rootDirectory.list()?.size ?: 0)
    }

    private fun getContext() = InstrumentationRegistry.getInstrumentation().targetContext!!

}
