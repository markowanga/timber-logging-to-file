package com.github.markowanga.timberloggingtofile.logname

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

/**
 * Created by Marcin on 20/02/2022.
 */
class ConstantDateTimeLogFileNameProviderTest {

    @Test
    fun getFileTag() {
        val dateTime = LocalDateTime.of(2022, 1, 1, 1, 1)
        val fileName = ConstantDateTimeLogFileNameProvider(dateTime)
            .getFileName(LocalDateTime.now())
        assertEquals("app_logs_20220101_0101.log", fileName)
    }

}