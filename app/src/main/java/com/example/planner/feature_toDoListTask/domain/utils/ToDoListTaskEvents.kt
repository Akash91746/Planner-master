package com.example.planner.feature_toDoListTask.domain.utils

import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask

sealed class ToDoListTaskEvents {

    data class Init(val listId: Int) : ToDoListTaskEvents()

    data class  OnDeleteTask(val value: ToDoListTask): ToDoListTaskEvents()

    object OnClickAdd: ToDoListTaskEvents()

    data class OnEditTask(val task: ToDoListTask): ToDoListTaskEvents()

    data class ToggleTaskDone(val task: ToDoListTask): ToDoListTaskEvents()

    data class ToggleFavoriteStatus(val task: ToDoListTask): ToDoListTaskEvents()

    object OnClickBack: ToDoListTaskEvents()

    data class OnToDoListTaskFormEvents(val formEvent: ToDoListTaskFormEvents): ToDoListTaskEvents()

    data class OnClickTaskInsight(val taskId: Int): ToDoListTaskEvents()
}
