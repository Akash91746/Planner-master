package com.example.planner.feature_toDoListTask.domain.utils

sealed class TrackerDialogEvents {

    object OnSubmit : TrackerDialogEvents()

    object OnClear: TrackerDialogEvents()

    data class OnChangeTitleOne(val value: String) : TrackerDialogEvents()

    data class OnChangeTitleTwo(val value: String) : TrackerDialogEvents()

    object ToggleEnableTitleTwo : TrackerDialogEvents()
}