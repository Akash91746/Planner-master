package com.example.planner.common.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun DateTimeFormatter.formatLocalDate(localDate: LocalDate, pattern: String? = null): String{

    val formatter = if(pattern == null){
        DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy")
    }else {
        DateTimeFormatter.ofPattern(pattern)
    }

    return localDate.format(formatter)
}