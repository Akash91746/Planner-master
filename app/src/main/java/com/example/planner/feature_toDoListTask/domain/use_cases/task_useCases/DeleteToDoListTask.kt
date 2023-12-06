package com.example.planner.feature_toDoListTask.domain.use_cases.task_useCases

import com.example.planner.feature_toDoList.domain.repository.ToDoListRepository
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.repository.ToDoListTaskRepository
import com.example.planner.feature_toDoListTask.domain.use_cases.task_reminder.CancelReminder

class DeleteToDoListTask(
    private val taskRepository: ToDoListTaskRepository,
    private val cancelReminder: CancelReminder,
    private val toDoListRepository: ToDoListRepository,
) {
    suspend operator fun invoke(task: ToDoListTask) {
        if (task.repeatMode == null)
            toDoListRepository.decrementTaskRemaining(task.listId)

        taskRepository.delete(toDoListTask = task)

        if (task.reminderTime != null) {
            cancelReminder(task.id)
        }
    }

}