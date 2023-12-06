package com.example.planner.feature_toDoListTask.domain.use_cases.task_useCases

import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import com.example.planner.feature_toDoList.domain.repository.ToDoListRepository
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.repository.ToDoListTaskRepository
import com.example.planner.feature_toDoListTask.domain.use_cases.task_reminder.TaskReminderUseCases

class UpdateToDoListTask(
    private val repository: ToDoListTaskRepository,
    private val reminderUseCases: TaskReminderUseCases,
    private val repeatTaskRepository: RepeatTaskRepository,
    private val toDoListRepository: ToDoListRepository,
) {

    suspend operator fun invoke(
        newTask: ToDoListTask,
        oldTask: ToDoListTask? = null,
    ) {
        repository.update(newTask)

        if (oldTask == null) return

        if (newTask.taskCompleted != oldTask.taskCompleted && newTask.repeatMode == null) {
            if(newTask.taskCompleted){
                toDoListRepository.decrementTaskRemaining(newTask.listId)
            }else {
                toDoListRepository.incrementTaskRemaining(newTask.listId)
            }
        }

        if (oldTask.reminderTime != newTask.reminderTime) {
            if (newTask.reminderTime != null) {
                reminderUseCases.createReminder(newTask)
            } else {
                reminderUseCases.cancelReminder(newTask.id)
            }
        }

        if (oldTask.repeatMode != newTask.repeatMode) {
            if (newTask.repeatMode != null) {
                val repeatTask = RepeatTask(
                    taskId = newTask.id,
                    timeStamp = newTask.timeStamp.toLocalDate()
                )
                repeatTaskRepository.insert(repeatTask)
            }
        }
    }
}