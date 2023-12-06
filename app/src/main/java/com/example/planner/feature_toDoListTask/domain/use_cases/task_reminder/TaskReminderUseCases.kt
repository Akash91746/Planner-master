package com.example.planner.feature_toDoListTask.domain.use_cases.task_reminder

data class TaskReminderUseCases(
    val createReminder: CreateReminder,
    val cancelReminder: CancelReminder
)
