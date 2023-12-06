package com.example.planner.feature_toDoListTask.domain.use_cases.task_reminder

import android.content.Context
import com.example.planner.broadcasts.TaskNotificationBroadcast
import com.example.planner.feature_reminder.domain.services.AlarmScheduler

class CancelReminder(
    private val alarmScheduler: AlarmScheduler,
    private val context: Context
) {

    operator fun invoke(id: Int) {

        val pendingIntent =
            TaskNotificationBroadcast.getPendingIntent(
                context,
                id
            )

        alarmScheduler.cancel(pendingIntent)
    }
}