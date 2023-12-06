package com.example.planner.feature_toDoListTask.domain.utils

import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask

sealed class BaseTaskEvents {
    data class ExpandTrackerDetails(
        val id: Int,
        val valueOne: String? = null,
        val valueTwo: String? = null,
    ) : BaseTaskEvents()

    data class OnChangeTrackerValueOne(val value: String) : BaseTaskEvents()
    data class OnChangeTrackerValueTwo(val value: String) : BaseTaskEvents()
    data class ToggleTaskDoneStatus(val value: ToDoListTaskType) : BaseTaskEvents()
    data class ToggleFavoriteStatus(val value: ToDoListTask) : BaseTaskEvents()
}
