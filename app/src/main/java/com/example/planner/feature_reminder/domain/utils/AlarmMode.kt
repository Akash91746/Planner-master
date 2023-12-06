package com.example.planner.feature_reminder.domain.utils

import com.example.planner.feature_reminder.domain.models.AlarmData
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

sealed class AlarmMode(val alarmData: AlarmData) {

    data class EveryDay(private val time: LocalTime) : AlarmMode(AlarmData(time))

    data class Weekly(
        private val time: LocalTime,
        val weekDays: List<DayOfWeek>,
    ) :
        AlarmMode(AlarmData(time))

    data class Monthly(private val time: LocalTime, val date: LocalDate) :
        AlarmMode(AlarmData(time, date))

    data class Yearly(private val time: LocalTime, val date: LocalDate) :
        AlarmMode(AlarmData(time, date))

    data class OneTime(private val time: LocalTime, val date: LocalDate) :
        AlarmMode(AlarmData(time, date))
}
