package com.github.markowanga.timberloggingtofile

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.markowanga.timberloggingtofile.crypt.Base64TextCrypt
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
        val rootDirectory = LogManager.getExternalLogsDirectory(getContext())
        LogManager.removeFilesOlderThanDays(0, rootDirectory)
        Timber.uprootAll()
    }

    @Test
    fun removeFilesOlderThan2Days() {
        val rootDirectory = LogManager.getExternalLogsDirectory(getContext())
        Timber.plant(LogToFileTimberTree(rootDirectory))
        Timber.plant(
            LogToFileTimberTree(rootDirectory, Base64TextCrypt(), logFilePrefix = "base64_")
        )
        Timber.i("hello log")
        LogManager.removeFilesOlderThanDays(2, rootDirectory)
        assertEquals(2, rootDirectory.list()?.size ?: 0)
    }

    @Test
    fun removeFilesOlderThan0Days() {
        val rootDirectory = LogManager.getExternalLogsDirectory(getContext())
        Timber.plant(LogToFileTimberTree(rootDirectory))
        Timber.plant(
            LogToFileTimberTree(rootDirectory, Base64TextCrypt(), logFilePrefix = "base64_")
        )
        Timber.i("hello log")
        LogManager.removeFilesOlderThanDays(0, rootDirectory)
        assertEquals(0, rootDirectory.list()?.size ?: 0)
    }

    private fun getContext() = InstrumentationRegistry.getInstrumentation().targetContext!!

}
