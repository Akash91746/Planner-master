package com.example.planner.feature_reminder.data.services

import android.app.PendingIntent
import com.example.planner.common.domain.services.AlarmService
import com.example.planner.feature_reminder.domain.services.AlarmScheduler
import com.example.planner.feature_reminder.domain.utils.*
import timber.log.Timber
import java.time.LocalDateTime

class AlarmSchedulerImpl(
    private val alarmService: AlarmService,
) : AlarmScheduler {

    private val alarmUtils by lazy { AlarmModeUtils() }

    override fun schedule(
        mode: AlarmMode,
        pendingIntent: PendingIntent,
        scheduleNextReminder: Boolean,
    ): LocalDateTime {
        val scheduleTime = mode.alarmData.time

        var scheduleDateTime = LocalDateTime.now()

        scheduleDateTime = scheduleDateTime.copyTime(scheduleTime)

        mode.alarmData.date?.let { date ->
            scheduleDateTime = scheduleDateTime.copyDate(date)
        }

        val isValidTime = alarmUtils.checkValidTimeStamp(mode)

        if (!isValidTime || scheduleNextReminder) {
            scheduleDateTime = alarmUtils.getNextTimeStamp(mode)
        }

        Timber.d("Alarm scheduled for ",scheduleDateTime)

        alarmService.schedule(scheduleDateTime.toMilliseconds(), pendingIntent)

        return scheduleDateTime
    }


    override fun cancel(pendingIntent: PendingIntent) {
        alarmService.cancel(pendingIntent)
    }
}