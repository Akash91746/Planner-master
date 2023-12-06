package com.example.planner.feature_toDoListTask.domain.use_cases.task_reminder

import android.content.Context
import com.example.planner.broadcasts.TaskNotificationBroadcast
import com.example.planner.feature_reminder.domain.services.AlarmScheduler
import com.example.planner.feature_reminder.domain.utils.AlarmMode
import com.example.planner.feature_reminder.domain.utils.AlarmModeUtils
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import java.time.*

class CreateReminder(
    private val alarmScheduler: AlarmScheduler,
    private val context: Context
) {
    operator fun invoke(
        taskDetails: ToDoListTask,
        scheduleNextReminder: Boolean = false,
    ): LocalDateTime? {
        val alarmModeUtils = AlarmModeUtils()
        val id = taskDetails.id

        val reminder = taskDetails.reminderTime ?: return null
        val repeatMode = taskDetails.repeatMode
        val reminderDate = taskDetails.date

        val pendingIntent = TaskNotificationBroadcast.getPendingIntent(
            context,
            id,
        )

        if (repeatMode == null) {
            return alarmScheduler.schedule(
                AlarmMode.OneTime(reminder, LocalDate.now()),
                pendingIntent,
                scheduleNextReminder
            )
        }

        val alarmMode = alarmModeUtils.getAlarmMode(
            repeatMode = repeatMode,
            time = reminder,
            date = reminderDate,
            weekDays = taskDetails.weekDays
        )

        return alarmScheduler.schedule(alarmMode, pendingIntent, scheduleNextReminder)
    }
}