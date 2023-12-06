package com.example.planner.feature_quotes.worker

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.planner.R
import com.example.planner.common.domain.services.NotificationService
import com.example.planner.feature_quotes.domain.useCases.QuotesUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class DailyQuoteFetchWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val quotesUseCases: QuotesUseCases,
    private val notificationService: NotificationService,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {

        val result = quotesUseCases.getQuote().getOrNull() ?: return Result.failure()

        notificationService.createNotificationChannel()

        if (NotificationManagerCompat.from(applicationContext).areNotificationsEnabled()) {

            val notificationTitle = applicationContext.getString(R.string.quote_of_the_day)
            val channelId = applicationContext.getString(R.string.task_reminder_channel_id)

            val notification = Notification.Builder(
                applicationContext,
                channelId
            ).setContentTitle(notificationTitle)
                .setContentText(result.quote)
                .setSmallIcon(R.drawable.ic_baseline_done_24)
                .build()

            notificationService.showNotification(notification, 1001)
        }

        return Result.success()
    }
}