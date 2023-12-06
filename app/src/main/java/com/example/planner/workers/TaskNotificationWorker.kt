package com.example.planner.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.planner.R
import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.feature_taskRepeat.data.repository.RepeatTaskRepositoryImpl
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_toDoListTask.data.repository.ToDoListTaskRepositoryImpl
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.use_cases.task_notification.TaskNotificationUseCases
import com.example.planner.feature_toDoListTask.domain.use_cases.task_reminder.TaskReminderUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TaskNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    plannerDatabase: PlannerDatabase,
    private val reminderUseCases: TaskReminderUseCases,
    private val taskNotificationUseCases: TaskNotificationUseCases,
) : CoroutineWorker(context, workerParameters) {

    private val taskRepository = ToDoListTaskRepositoryImpl(plannerDatabase.toDoListTaskDao)
    private val repeatTaskRepository = RepeatTaskRepositoryImpl(plannerDatabase.repeatTaskDao)

    override suspend fun doWork(): Result {

        val id = inputData.getInt(applicationContext.getString(R.string.task_id_key), Int.MAX_VALUE)

        if (id == Int.MAX_VALUE) return Result.failure()

        val task = taskRepository.getTaskById(id) ?: return Result.failure()

        if (!task.taskCompleted) {
            taskNotificationUseCases.createTaskNotification(task.id, task.title)
        }

        scheduleNextTask(task)

        return Result.success()
    }

    private suspend fun scheduleNextTask(toDoListTask: ToDoListTask) {
        if (toDoListTask.repeatMode == null) return

        val nextNotificationTimeStamp = reminderUseCases.createReminder(toDoListTask, true)

        nextNotificationTimeStamp?.let {
            val repeatTask =
                RepeatTask(
                    taskId = toDoListTask.id,
                    timeStamp = nextNotificationTimeStamp.toLocalDate()
                )

            repeatTaskRepository.insert(repeatTask).toInt()
        }
    }
}