package com.example.planner.feature_toDoListTask.domain.utils

import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import com.example.planner.feature_toDoListTask.domain.models.TrackerDetails
import java.time.DayOfWeek

object ToDoListStateConverter {

    fun toToDoListTask(
        state: ToDoListTaskState, listId: Int,
    ): ToDoListTask {
        val formState = state.formState
        val repeatDialogState = formState.repeatDialogState

        val date = repeatDialogState.date
        val selectedWeekDays: List<DayOfWeek> =
            repeatDialogState.weekDays.filter { it.selected }.map { it.day }

        val weekDays =
            if (formState.repeatDialogState.repeatMode != RepeatMode.WEEKLY || selectedWeekDays.isEmpty()) {
                null
            } else {
                selectedWeekDays
            }


        val tracker = if (repeatDialogState.repeatMode != null) {
            val trackerDialogState = formState.trackerDialogState
            if (trackerDialogState.selected) {
                TrackerDetails(
                    titleOne = trackerDialogState.titleOne,
                    titleTwo = if (trackerDialogState.titleTwoEnabled) trackerDialogState.titleTwo else null
                )
            } else null
        } else null


        if (state.selectedTask == null) {

            return ToDoListTask(
                title = formState.title,
                color = formState.color,
                listId = listId,
                repeatMode = repeatDialogState.repeatMode,
                date = date,
                weekDays = weekDays,
                tracker = tracker,
                reminderTime = formState.reminder
            )

        } else {
            return state.selectedTask.copy(
                title = formState.title,
                color = formState.color,
                repeatMode = repeatDialogState.repeatMode,
                date = date,
                weekDays = weekDays,
                tracker = tracker,
                reminderTime = formState.reminder
            )
        }

    }

    fun toToDoListTaskFormState(
        task: ToDoListTask,
    ): ToDoListTaskFormState {

        val weekDays = if (task.weekDays != null)
            WEEK_DAY_LIST.map {
                return@map it.copy(selected = task.weekDays.contains(it.day))
            } else WEEK_DAY_LIST

        val trackerDialogState = if (task.tracker != null) {
            TrackerDialogState(
                titleOne = task.tracker.titleOne,
                titleTwo = task.tracker.titleTwo ?: "",
            )
        } else {
            TrackerDialogState()
        }

        with(task) {
            return ToDoListTaskFormState(
                title = title,
                color = color,
                repeatDialogState = RepeatDialogState(
                    date = date,
                    repeatMode = repeatMode,
                    weekDays = weekDays
                ),
                trackerDialogState = trackerDialogState.copy(selected = tracker != null),
                reminder = reminderTime
            )
        }
    }
}