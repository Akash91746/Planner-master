package com.example.planner.feature_toDoListTask.domain.use_cases.task_useCases

import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import com.example.planner.feature_toDoList.domain.repository.ToDoListRepository
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.repository.ToDoListTaskRepository
import com.example.planner.feature_toDoListTask.domain.use_cases.task_reminder.TaskReminderUseCases

class InsertToDoListTask(
    private val toDoListTaskRepository: ToDoListTaskRepository,
    private val reminderUseCases: TaskReminderUseCases,
    private val repeatTaskRepository: RepeatTaskRepository,
    private val toDoListRepository: ToDoListRepository,
) {

    suspend operator fun invoke(
        toDoListTask: ToDoListTask,
    ) {
        val taskId = toDoListTaskRepository.insert(toDoListTask).toInt()

        if (toDoListTask.repeatMode != null) {
            repeatTaskRepository.insert(
                RepeatTask(
                    taskId = taskId,
                    timeStamp = toDoListTask.timeStamp.toLocalDate()
                )
            )
        } else {
            toDoListRepository.incrementTaskRemaining(toDoListTask.listId)
        }

        if (toDoListTask.reminderTime != null) {
            reminderUseCases.createReminder(toDoListTask.copy(id = taskId))
        }
    }
}