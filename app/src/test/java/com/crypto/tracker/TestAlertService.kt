package com.crypto.tracker

import android.content.Context
import androidx.work.ListenableWorker.Result
import androidx.work.testing.TestListenableWorkerBuilder
import com.crypto.tracker.utils.service.AlertService
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RefreshMainDataWorkTest {
    private lateinit var context: Context
    @Before
    fun setup() {
        context = appContext!!
    }
    @Test
    fun testRefreshMainDataWork() {
        // Get the AlertWorker
        val worker = TestListenableWorkerBuilder<AlertService>(context).build()
        // Start the work synchronously
        val result = worker.startWork().get()
        assertThat(result, `is`(Result.success()))
    }
}
