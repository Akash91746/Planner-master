package com.example.planner.feature_toDoListTask.domain.utils

import java.time.LocalDate

data class RepeatDialogState(
    val date: LocalDate? = null,
    val errorMessage: String? = null,
    val repeatMode: RepeatMode? = null,
    val weekDays: List<WeekDay> = WEEK_DAY_LIST
)
