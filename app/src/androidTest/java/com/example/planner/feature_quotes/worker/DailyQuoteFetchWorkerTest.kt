package com.example.planner.feature_quotes.worker

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Rule

@HiltAndroidTest
@RunWith(JUnit4::class)
class DailyQuoteFetchWorkerTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var context: Context

    @Before
    fun setup(){
        hiltRule.inject()
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testDailyQuotesWork(){

        val worker = TestListenableWorkerBuilder<DailyQuoteFetchWorker>(context).build()

        val result = worker.startWork().get()

        assertThat(result).isEqualTo(ListenableWorker.Result.success())
    }

}