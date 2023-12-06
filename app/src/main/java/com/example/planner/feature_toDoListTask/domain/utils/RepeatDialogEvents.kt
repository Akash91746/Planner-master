package com.example.planner.feature_toDoListTask.domain.utils

import java.time.LocalDate

sealed class RepeatDialogEvents {

    object OnClickClear: RepeatDialogEvents()

    object OnSubmit: RepeatDialogEvents()

    data class OnChangeRepeatMode(val repeatMode: RepeatMode): RepeatDialogEvents()

    data class OnChangeWeekDaySelection(val weekDay: WeekDay): RepeatDialogEvents()

    data class OnChangeDate(val date: LocalDate): RepeatDialogEvents()
}
