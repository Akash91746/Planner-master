package com.example.planner.feature_toDoListTask.domain.use_cases.task_notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.planner.MainActivity
import com.example.planner.R
import com.example.planner.broadcasts.TaskDoneBroadcastReceiver
import com.example.planner.common.domain.services.NotificationService

class CreateTaskNotification(
    private val notificationService: NotificationService,
    private val context: Context
) {

    operator fun invoke(
        id: Int,
        title: String
    ) {

        val channelId = context.getString(R.string.task_reminder_channel_id)

        val activityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            putExtra(context.getString(R.string.task_id_key), id)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            id,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val taskDonePendingIntent = TaskDoneBroadcastReceiver.getPendingIntent(
            context, id
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle(title)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .addAction(
                R.drawable.ic_baseline_done_24,
                context.getString(R.string.notification_done_button),
                taskDonePendingIntent
            ).setAutoCancel(true)

        return notificationService.showNotification(notification.build(), id)
    }
}