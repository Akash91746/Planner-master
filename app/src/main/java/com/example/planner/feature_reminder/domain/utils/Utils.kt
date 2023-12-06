package com.example.planner.feature_reminder.domain.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

fun LocalDateTime.toMilliseconds(): Long{
    return atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun LocalDateTime.copyDate(localDate: LocalDate): LocalDateTime{
    return withYear(localDate.year).withMonth(localDate.monthValue).withDayOfMonth(localDate.dayOfMonth)
}

fun LocalDateTime.copyTime(localTime: LocalTime): LocalDateTime{
    return withHour(localTime.hour).withMinute(localTime.minute).withSecond(localTime.second)
}