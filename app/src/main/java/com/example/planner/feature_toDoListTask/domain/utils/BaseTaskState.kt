package com.example.planner.feature_toDoListTask.domain.utils

data class BaseTaskState(
    val currentExpandedItemId: Int? = null,
    val trackerValueTwo: String = "",
    val trackerValueOne: String = "",
    val trackerErrorMessage: String? = null
)
