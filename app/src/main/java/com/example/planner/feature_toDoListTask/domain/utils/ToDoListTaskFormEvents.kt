package com.example.planner.feature_toDoListTask.domain.utils

import java.time.LocalTime

sealed class ToDoListTaskFormEvents{

    data class OnChangeTitle(val value: String): ToDoListTaskFormEvents()
    data class OnChangeColor(val value: Int): ToDoListTaskFormEvents()
    data class OnChangeReminderTime(val value: LocalTime?): ToDoListTaskFormEvents()

    object ToggleTrackerDialog : ToDoListTaskFormEvents()
    object ToggleShowRepeatDialog: ToDoListTaskFormEvents()

    object ResetForm: ToDoListTaskFormEvents()
    object OnSubmit: ToDoListTaskFormEvents()
    object HideKeyboard: ToDoListTaskFormEvents()

    data class OnRepeatDialogEvent(val event: RepeatDialogEvents): ToDoListTaskFormEvents()
    data class OnTrackerDialogEvent(val event: TrackerDialogEvents): ToDoListTaskFormEvents()
}
