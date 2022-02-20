package com.github.markowanga.timberloggingtofile

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.github.markowanga.timberloggingtofile.crypt.Base64TextCrypt
import com.github.markowanga.timberloggingtofile.logname.DailyLogFileNameProvider
import com.github.markowanga.timberloggingtofile.storage.ExternalLogStorageProvider
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RunWith(AndroidJUnit4::class)
class LogToFileTimberTreeTest {

    @Before
    @After
    fun resetAll() {
        resetAll(getContext())
    }

    @Test
    fun testContentOfNoCryptedLogs() {
        val storageProvider = ExternalLogStorageProvider(getContext())
        val rootDirectory = storageProvider.getStorageDirectory()
        Timber.plant(LogToFileTimberTree(rootDirectory))
        Timber.i(EXAMPLE_LOG_MESSAGE_1)
        Assert.assertEquals(
            true,
            getFirstFileInDirectory(rootDirectory).readText().contains(EXAMPLE_LOG_MESSAGE_1)
        )
        Assert.assertEquals(
            false,
            getFirstFileInDirectory(rootDirectory).readText().contains(EXAMPLE_LOG_MESSAGE_2)
        )
    }

    @Test
    fun testBasicFileName() {
        val storageProvider = ExternalLogStorageProvider(getContext())
        val rootDirectory = storageProvider.getStorageDirectory()
        Timber.plant(LogToFileTimberTree(rootDirectory))
        Timber.i(EXAMPLE_LOG_MESSAGE_1)
        val dateTag = DateTimeFormatter.ofPattern("yyyyMMdd")!!.format(LocalDateTime.now())
        Assert.assertEquals(
            "app_logs_$dateTag.log",
            getFirstFileInDirectory(rootDirectory).name
        )
    }

    @Test
    fun testParametrizedFileName() {
        val storageProvider = ExternalLogStorageProvider(getContext())
        val rootDirectory = storageProvider.getStorageDirectory()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")!!
        Timber.plant(
            LogToFileTimberTree(
                rootDirectory,
                logFileNameProvider = DailyLogFileNameProvider(prefix = "my_log_", extension = "ml")
            )
        )
        Timber.i(EXAMPLE_LOG_MESSAGE_1)
        val dateTag = formatter.format(LocalDateTime.now())
        Assert.assertEquals(
            "my_log_$dateTag.ml",
            getFirstFileInDirectory(rootDirectory).name
        )
    }

    @Test
    fun testCryptedLogContent() {
        val storageProvider = ExternalLogStorageProvider(getContext())
        val rootDirectory = storageProvider.getStorageDirectory()
        val cryptText = Base64TextCrypt()
        Timber.plant(LogToFileTimberTree(rootDirectory, cryptText))
        Timber.i(EXAMPLE_LOG_MESSAGE_1)
        val logLines = getFirstFileInDirectory(rootDirectory)
            .readLines().map { cryptText.decryptText(it) }
        Assert.assertEquals(1, logLines.filter { it.contains(EXAMPLE_LOG_MESSAGE_1) }.size)
    }

    private fun getFirstFileInDirectory(root: File) =
        root.listFiles()?.toList()?.first() ?: throw Exception("No files in directory")

    private fun getContext() = InstrumentationRegistry.getInstrumentation().targetContext!!

    companion object {
        const val EXAMPLE_LOG_MESSAGE_1 = "hello log"
        const val EXAMPLE_LOG_MESSAGE_2 = "test logging"
    }

}
