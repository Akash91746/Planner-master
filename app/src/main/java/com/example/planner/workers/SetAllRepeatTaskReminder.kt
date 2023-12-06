package com.example.planner.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.feature_toDoListTask.data.repository.ToDoListTaskRepositoryImpl
import com.example.planner.feature_toDoListTask.domain.use_cases.task_reminder.TaskReminderUseCases
import kotlinx.coroutines.flow.firstOrNull

class SetAllRepeatTaskReminder(
    context: Context,
    params: WorkerParameters,
    plannerDatabase: PlannerDatabase,
    private val reminderUseCases: TaskReminderUseCases,
) : CoroutineWorker(context, params) {

    private val toDoListTaskRepository = ToDoListTaskRepositoryImpl(plannerDatabase.toDoListTaskDao)

    override suspend fun doWork(): Result {

        val tasks =
            toDoListTaskRepository.getRepeatingTasks().firstOrNull() ?: return Result.failure()

        tasks.forEach { task ->
            reminderUseCases.createReminder(task)
        }

        return Result.success()
    }

}