package com.example.planner.feature_toDoListTask.domain.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import javax.annotation.Nullable

data class TrackerDetails(
    val titleOne: String,
    val titleTwo: String? = null,
)

object TrackerDetailsConverter {

    @TypeConverter
    @Nullable
    fun toString(trackerDetails: TrackerDetails?): String? {
        trackerDetails?.let {
            try {
                val gson = Gson()
                return gson.toJson(trackerDetails)
            } catch (e: Error) {
                return null
            }
        }

        return null
    }

    @TypeConverter
    @Nullable
    fun toTrackerDetails(value: String?): TrackerDetails? {
        value?.let {
            try {
                val gson = Gson()
                return gson.fromJson(value, TrackerDetails::class.java)
            } catch (e: Error) {
                return null
            }
        }

        return null
    }
}
