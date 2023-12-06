package com.example.planner.feature_toDoListTask.domain.utils

import android.graphics.Color
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import java.time.LocalTime

data class ToDoListTaskFormState(
    val title: String = "",
    val titleErrorMessage: String? = null,
    val color: Int = Color.TRANSPARENT,
    val showTrackerDialog: Boolean = false,
    val showRepeatDialog: Boolean = false,
    val repeatDialogState: RepeatDialogState = RepeatDialogState(),
    val trackerDialogState: TrackerDialogState = TrackerDialogState(),
    val reminder: LocalTime? = null
)
