package com.example.planner

import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.work.*
import com.example.planner.feature_quotes.worker.DailyQuoteFetchWorker
import com.example.planner.ui.theme.PlannerTheme
import com.example.planner.workers.SetupCurrentDayTaskWorker
import dagger.hilt.android.AndroidEntryPoint
import java.lang.RuntimeException
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlannerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }

        notificationPermissionCheck()

        enqueuePeriodicTaskWorker()
        enqueueTaskWorker()
        enqueuePeriodicDailyQuotesWorker()
    }

    private fun enqueueTaskWorker() {
        val currentDay = LocalDate.now()

        val lastInitDay =
            sharedPreferences.getString(SetupCurrentDayTaskWorker.LAST_TASK_SETUP_DATE_KEY, null)

        if (lastInitDay != null) {
            if (currentDay.toString() == lastInitDay) return
        }

        val worker = OneTimeWorkRequestBuilder<SetupCurrentDayTaskWorker>()

        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
            applicationContext.getString(R.string.set_up_worker_unique_name),
            ExistingWorkPolicy.KEEP,
            worker.build()
        )
    }

    private fun enqueuePeriodicTaskWorker() {

        val flexTime: Long = calculateFlex()

        val workRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
            SetupCurrentDayTaskWorker::class.java,
            1,
            TimeUnit.DAYS,
            flexTime,
            TimeUnit.MILLISECONDS
        )
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            getString(R.string.set_up_worker_periodic_unique_name), ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun enqueuePeriodicDailyQuotesWorker() {
        val flexTime = calculateFlex()

        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<DailyQuoteFetchWorker>(
            24,
            TimeUnit.DAYS,
            flexTime,
            TimeUnit.MILLISECONDS
        )
            .setConstraints(workConstraints)
            .build()

        WorkManager
            .getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                getString(R.string.set_up_daily_quote_worker_name),
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }

    private fun calculateFlex(hourOfTheDay: Int = 1, repeatInterval: Int = 1): Long {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfTheDay)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        val startDelay: Long =
            calendar.timeInMillis - System.currentTimeMillis()
        val intervalMillis: Long = TimeUnit.DAYS.toMillis(repeatInterval.toLong())

        return intervalMillis - startDelay
    }


    private fun notificationPermissionCheck() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            val granted = checkSelfPermission(permission)

            if (granted == PackageManager.PERMISSION_DENIED) {
                requestPermissionLauncher.launch(permission)
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    AppNavHost(navController = navController)
}

