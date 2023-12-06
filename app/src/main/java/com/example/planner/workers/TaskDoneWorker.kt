package com.example.planner.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.planner.R
import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.feature_taskRepeat.data.repository.RepeatTaskRepositoryImpl
import com.example.planner.feature_toDoListTask.data.repository.ToDoListTaskRepositoryImpl
import com.example.planner.feature_toDoListTask.domain.use_cases.task_notification.TaskNotificationUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate

@HiltWorker
class TaskDoneWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    plannerDatabase: PlannerDatabase,
    private val notificationUseCases: TaskNotificationUseCases
) : CoroutineWorker(
    context, workerParameters
) {

    private val taskRepository = ToDoListTaskRepositoryImpl(plannerDatabase.toDoListTaskDao)
    private val repeatTaskRepository = RepeatTaskRepositoryImpl(plannerDatabase.repeatTaskDao)

    override suspend fun doWork(): Result {

        val taskId =
            inputData.getInt(applicationContext.getString(R.string.task_id_key), Int.MAX_VALUE)

        if (taskId == Int.MAX_VALUE ) return Result.failure()

        val task = taskRepository.getTaskById(taskId) ?: return Result.failure()

        val isRepeatTask = task.repeatMode != null

        if(!isRepeatTask){
            taskRepository.update(
                task.copy(taskCompleted = true)
            )
        } else {
           val repeatTask =  repeatTaskRepository.getRepeatTaskWithTimeStamp(taskId, LocalDate.now()) ?: return Result.failure()

            repeatTaskRepository.update(
                repeatTask.copy(
                    taskDone = true
                )
            )
        }

        notificationUseCases.cancelTaskNotification(taskId)

        return Result.success()
    }
}