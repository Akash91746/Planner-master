package com.example.planner.feature_toDoListTask.domain.utils

import androidx.room.TypeConverter
import org.json.JSONArray
import java.time.DayOfWeek
import javax.annotation.Nullable

enum class RepeatMode {
    EVERYDAY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

object RepeatModeConverter {
    @TypeConverter
    @Nullable
    fun toString(repeatMode: RepeatMode?): String? = repeatMode?.name


    @TypeConverter
    @Nullable
    fun toRepeatMode(repeatMode: String?): RepeatMode? {
        return if (repeatMode != null) enumValueOf<RepeatMode>(repeatMode) else null
    }
}

object WeekDaysConverter {

    @TypeConverter
    @Nullable
    fun toString(list: List<DayOfWeek>?): String? {
        if (list == null) {
            return null
        }
        val json = JSONArray(list)

        return json.toString()
    }

    @TypeConverter
    @Nullable
    fun toList(jsonString: String?): List<DayOfWeek>? {
        if (jsonString == null) {
            return null
        }
        val json = JSONArray(jsonString)
        val array = arrayListOf<DayOfWeek>()
        for (i in 0 until json.length()) {
            array.add(DayOfWeek.valueOf(json.getString(i)))
        }
        return array
    }
}
