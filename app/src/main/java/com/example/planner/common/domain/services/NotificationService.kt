package com.example.planner.common.domain.services

import android.app.Notification
import android.app.NotificationManager

interface NotificationService {
    fun createNotificationChannel()

    fun showNotification(notification: Notification, id: Int)

    fun cancelNotification(id: Int)

    fun getNotificationManager(): NotificationManager
}