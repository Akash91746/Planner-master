package com.example.planner.feature_toDoListTask.domain.utils

import java.time.DayOfWeek
import java.util.*

data class WeekDay(
    val day: DayOfWeek,
    val title: String,
    val selected: Boolean = true
)

val WEEK_DAY_LIST = listOf(
    WeekDay(DayOfWeek.SUNDAY, "SU"),
    WeekDay(DayOfWeek.MONDAY,"MO"),
    WeekDay(DayOfWeek.TUESDAY,"TU"),
    WeekDay(DayOfWeek.WEDNESDAY,"WE"),
    WeekDay(DayOfWeek.THURSDAY,"TH"),
    WeekDay(DayOfWeek.FRIDAY,"FR"),
    WeekDay(DayOfWeek.SATURDAY,"SA")
)