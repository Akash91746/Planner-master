package com.example.planner.common.utils

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.annotation.Nullable

object TimeStampConverter {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    @Nullable
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return if (value != null) formatter.parse(value, OffsetDateTime::from) else null
    }

    @TypeConverter
    @JvmStatic
    @Nullable
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }
}

object LocalDateConverter {

    @TypeConverter
    @Nullable
    fun toLocalDate(value: String?): LocalDate? {
        return if (value != null) LocalDate.parse(value) else null
    }

    @TypeConverter
    @Nullable
    fun toString(localDate: LocalDate?): String? {
        return localDate?.toString()
    }
}

object LocalTimeConverter {

    @TypeConverter
    @Nullable
    fun toLocalTime(value: String?): LocalTime? {
        return if (value != null) LocalTime.parse(value) else null
    }

    @TypeConverter
    @Nullable
    fun toString(localTime: LocalTime?): String? {
        return localTime?.toString()
    }

}