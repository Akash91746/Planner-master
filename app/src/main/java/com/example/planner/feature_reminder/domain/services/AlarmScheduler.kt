package com.example.planner.feature_reminder.domain.services

import android.app.PendingIntent
import com.example.planner.feature_reminder.domain.utils.AlarmMode
import java.time.LocalDateTime

interface AlarmScheduler {

    fun schedule(mode: AlarmMode,pendingIntent: PendingIntent,scheduleNextReminder: Boolean = false): LocalDateTime

    fun cancel(pendingIntent: PendingIntent)
}