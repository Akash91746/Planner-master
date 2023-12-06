package com.example.planner.common.data.services

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.example.planner.R
import com.example.planner.common.domain.services.NotificationService

class NotificationServiceImpl(
    private val context: Context
): NotificationService {

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    override fun createNotificationChannel() {
        val name = context.getString(R.string.reminder_channel_name)
        val desc = context.getString(R.string.reminder_channel_desc)
        val channelId = context.getString(R.string.task_reminder_channel_id)

        val channel = NotificationChannel(
            channelId,
            name,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = desc
        }

        notificationManager.createNotificationChannel(channel)
    }

    override fun showNotification(notification: Notification, id: Int) {
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(id, notification)
        }
    }

    override fun cancelNotification(id: Int) {
        with(NotificationManagerCompat.from(context)) {
            cancel(id)
        }
    }

    override fun getNotificationManager(): NotificationManager {
        return notificationManager
    }

}