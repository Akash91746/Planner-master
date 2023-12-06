package com.example.planner.feature_toDoListTask.domain.use_cases.task_notification

import com.example.planner.common.domain.services.NotificationService

class CancelTaskNotification(
    private val notificationService: NotificationService
) {

    operator fun invoke(taskId: Int){
        return notificationService.cancelNotification(taskId)
    }

}