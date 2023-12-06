package com.example.planner.feature_reminder.domain.models

import java.time.LocalDate
import java.time.LocalTime

data class AlarmData(
    val time: LocalTime,
    val date: LocalDate? = null
)
