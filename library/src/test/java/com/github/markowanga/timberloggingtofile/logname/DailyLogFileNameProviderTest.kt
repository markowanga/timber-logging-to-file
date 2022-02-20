package com.github.markowanga.timberloggingtofile.logname

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class DailyLogFileNameProviderTest {

    @Test
    fun getFileTag() {
        val dateTime = LocalDateTime.of(2022, 1, 1, 1, 1)
        assertEquals(
            "app_logs_20220101.log",
            DailyLogFileNameProvider().getFileName(dateTime)
        )
    }

}