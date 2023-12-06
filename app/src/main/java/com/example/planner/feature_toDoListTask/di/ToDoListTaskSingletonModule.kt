package com.example.planner.feature_toDoListTask.di

import android.content.Context
import com.example.planner.common.domain.services.NotificationService
import com.example.planner.feature_reminder.domain.services.AlarmScheduler
import com.example.planner.feature_toDoListTask.domain.use_cases.task_notification.CancelTaskNotification
import com.example.planner.feature_toDoListTask.domain.use_cases.task_notification.CreateTaskNotification
import com.example.planner.feature_toDoListTask.domain.use_cases.task_notification.TaskNotificationUseCases
import com.example.planner.feature_toDoListTask.domain.use_cases.task_reminder.CancelReminder
import com.example.planner.feature_toDoListTask.domain.use_cases.task_reminder.CreateReminder
import com.example.planner.feature_toDoListTask.domain.use_cases.task_reminder.TaskReminderUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToDoListTaskSingletonModule {

    @Provides
    @Singleton
    fun providesTaskReminderUseCases(
        alarmScheduler: AlarmScheduler,
        @ApplicationContext context: Context
    ): TaskReminderUseCases {
        return TaskReminderUseCases(
            createReminder = CreateReminder(alarmScheduler,context),
            cancelReminder = CancelReminder(alarmScheduler,context)
        )
    }

    @Provides
    @Singleton
    fun providesTaskNotificationUseCases(
        notificationService: NotificationService,
        @ApplicationContext context: Context
    ): TaskNotificationUseCases {
        return TaskNotificationUseCases(
            createTaskNotification = CreateTaskNotification(notificationService,context),
            cancelTaskNotification = CancelTaskNotification(notificationService)
        )
    }

}