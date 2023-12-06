package com.example.planner.feature_reminder.domain.utils

import com.example.planner.feature_toDoListTask.domain.utils.RepeatMode
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class AlarmModeUtils {

    fun getNextTimeStamp(alarmMode: AlarmMode): LocalDateTime {
        val scheduleTime = alarmMode.alarmData.time
        val scheduleDate = alarmMode.alarmData.date

        val scheduleDateTime = LocalDateTime.now().copyTime(scheduleTime)

        return when (alarmMode) {
            is AlarmMode.EveryDay -> {
                scheduleDateTime.plusDays(1)
            }
            is AlarmMode.Weekly -> {
                val weekDaysToRepeat = alarmMode.weekDays
                val currentDate = LocalDate.now()

                val repeatingDate = getNextRepeatingWeekDay(currentDate, weekDaysToRepeat)

                scheduleDateTime.copyDate(repeatingDate)
            }
            else -> {
                if (scheduleDate == null) throw InvalidArgumentException()

                return when (alarmMode) {
                    is AlarmMode.Monthly -> {
                        scheduleDateTime.withDayOfMonth(scheduleDate.dayOfMonth).plusMonths(1)
                    }
                    is AlarmMode.Yearly -> {
                        scheduleDateTime.withDayOfMonth(scheduleDate.dayOfMonth)
                            .withMonth(scheduleDate.monthValue).plusYears(1)
                    }
                    is AlarmMode.OneTime -> {
                        scheduleDateTime.copyDate(scheduleDate)
                    }
                    else -> scheduleDateTime
                }
            }
        }
    }

    fun getAlarmMode(
        repeatMode: RepeatMode,
        time: LocalTime,
        date: LocalDate? = null,
        weekDays: List<DayOfWeek>? = null,
    ): AlarmMode {
        return when (repeatMode) {
            RepeatMode.EVERYDAY -> {
                AlarmMode.EveryDay(time = time)
            }
            RepeatMode.MONTHLY -> {
                AlarmMode.Monthly(time = time, date = date!!)
            }
            RepeatMode.WEEKLY -> {
                AlarmMode.Weekly(time = time, weekDays = weekDays!!)
            }
            RepeatMode.YEARLY -> {
                AlarmMode.Yearly(time = time, date = date!!)
            }
        }
    }


    fun checkValidTimeStamp(
        alarmMode: AlarmMode,
        currentDateTime: LocalDateTime = LocalDateTime.now(),
    ): Boolean {
        val scheduleTime = alarmMode.alarmData.time

        val currentDate = currentDateTime.toLocalDate()
        val currentTime = currentDateTime.toLocalTime()

        val scheduleTimeBeforeCurrentTime = scheduleTime.isBefore(currentTime)

        if(alarmMode is AlarmMode.EveryDay){
            return !scheduleTimeBeforeCurrentTime
        }

        if (alarmMode is AlarmMode.Weekly) {
            val weekDays = alarmMode.weekDays

            val containWeekDay = weekDays.contains(currentDate.dayOfWeek)

            return containWeekDay && scheduleTimeBeforeCurrentTime
        }

        val scheduleDate = alarmMode.alarmData.date ?: throw InvalidArgumentException()

        if (alarmMode is AlarmMode.Monthly || alarmMode is AlarmMode.Yearly) {

            return currentDate.isBefore(scheduleDate) && scheduleTimeBeforeCurrentTime
        }

        return true
    }


    private fun getNextRepeatingWeekDay(
        date: LocalDate,
        weekDays: List<DayOfWeek>,
    ): LocalDate {

        val dayOfWeek = date.dayOfWeek

        if (weekDays.contains(dayOfWeek)) {
            return date
        }

        return getNextRepeatingWeekDay(date.plusDays(1), weekDays)
    }
}