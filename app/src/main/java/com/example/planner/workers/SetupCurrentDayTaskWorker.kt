package com.example.planner.workers

import android.content.Context
import android.content.SharedPreferences
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.planner.R
import com.example.planner.common.data.data_source.local.PlannerDatabase
import com.example.planner.feature_taskRepeat.data.repository.RepeatTaskRepositoryImpl
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_toDoListTask.data.repository.ToDoListTaskRepositoryImpl
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.use_cases.task_reminder.TaskReminderUseCases
import com.example.planner.feature_toDoListTask.domain.utils.RepeatMode
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate

@HiltWorker
class SetupCurrentDayTaskWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    plannerDatabase: PlannerDatabase,
    private val reminderUseCases: TaskReminderUseCases,
    private val sharedPreferences: SharedPreferences,
) : CoroutineWorker(context, params) {

    private val taskRepository = ToDoListTaskRepositoryImpl(plannerDatabase.toDoListTaskDao)
    private val repeatTaskRepository = RepeatTaskRepositoryImpl(plannerDatabase.repeatTaskDao)

    override suspend fun doWork(): Result {
        val currentDate = LocalDate.now()

        val todayRepeatTask = repeatTaskRepository.getRepeatTasks(LocalDate.now()).firstOrNull()
        val repeatingTasks =
            taskRepository.getRepeatingTasks().firstOrNull() ?: return Result.failure()

        val currentDayTasks = repeatingTasks.filter { isCurrentDayTask(it, currentDate) }

        currentDayTasks.forEach { task ->

            todayRepeatTask?.let {

                val containsRepeatTask =
                    todayRepeatTask.find { repeatTask -> repeatTask.taskId == task.id } != null

                if (!containsRepeatTask) {
                    val repeatTask = RepeatTask(taskId = task.id, timeStamp = currentDate)
                    repeatTaskRepository.insert(repeatTask = repeatTask)
                }
            }

            reminderUseCases.createReminder(task, true)
        }

        sharedPreferences.edit().putString(LAST_TASK_SETUP_DATE_KEY, currentDate.toString()).apply()

        return Result.success()
    }

    private fun isCurrentDayTask(
        task: ToDoListTask,
        currentDay: LocalDate = LocalDate.now(),
    ): Boolean {

        if (task.repeatMode == null) return false

        return when (task.repeatMode) {
            RepeatMode.EVERYDAY -> true
            RepeatMode.WEEKLY -> {
                val weekDays = task.weekDays
                weekDays?.contains(currentDay.dayOfWeek) ?: false
            }
            RepeatMode.MONTHLY, RepeatMode.YEARLY -> {
                val date = task.date
                if (date != null) currentDay == date else false
            }
        }
    }

    companion object {
        const val LAST_TASK_SETUP_DATE_KEY = "com.example.planner.last_task_setup_date_key"
    }

}