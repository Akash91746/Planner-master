package com.example.planner.common.utils

import android.app.DatePickerDialog
import android.content.Context
import java.time.LocalDate
import java.util.*
import javax.annotation.Nonnull

class PlannerDatePicker(
    @Nonnull private val mContext: Context,
    private val mYear: Int,
    private val mMonth: Int,
    private val mDayOfMonth: Int,
    listener: OnDateSetListener?,
) : DatePickerDialog(mContext, listener, mYear, mMonth, mDayOfMonth) {

    fun setDateRange(max: DatePickerRange) {
        when (max) {
            is DatePickerRange.YEAR -> {
                setYearRange()
            }
            is DatePickerRange.MONTH -> {
                setMonthRange()
            }
            is DatePickerRange.MaxDate -> {
                setMaxDate(max.date)
            }
        }
    }

    private fun setMaxDate(date: LocalDate){
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.YEAR,date.year)
        calendar.set(Calendar.MONTH,date.month.value - 1)
        calendar.set(Calendar.DAY_OF_MONTH,date.dayOfMonth)

        datePicker.maxDate = calendar.timeInMillis
    }

    private fun setYearRange() {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.YEAR, mYear)
        calendar.set(Calendar.MONTH, mMonth)
        calendar.set(Calendar.DAY_OF_MONTH, mDayOfMonth)

        datePicker.minDate = calendar.timeInMillis

        calendar.set(Calendar.YEAR, mYear + 1)
        calendar.set(Calendar.DAY_OF_MONTH, mDayOfMonth)

        datePicker.maxDate = calendar.timeInMillis
    }

    private fun setMonthRange() {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.YEAR, mYear)
        calendar.set(Calendar.MONTH, mMonth)
        calendar.set(Calendar.DAY_OF_MONTH, mDayOfMonth)

        datePicker.minDate = calendar.timeInMillis

        if (mMonth == Calendar.DECEMBER) {
            val year = mYear + 1
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, Calendar.JANUARY)
        } else {
            calendar.set(Calendar.MONTH, mMonth + 1)
        }

        calendar.set(Calendar.DAY_OF_MONTH, mDayOfMonth)

        datePicker.maxDate = calendar.timeInMillis
    }

    sealed class DatePickerRange {
        object MONTH : DatePickerRange()
        object YEAR : DatePickerRange()
        data class MaxDate(val date: LocalDate): DatePickerRange()
    }
}