package com.example.planner.feature_toDoListTask.domain.models

import android.graphics.Color
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.planner.feature_toDoList.domain.models.ToDoList
import com.example.planner.feature_toDoListTask.domain.utils.RepeatMode
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime

@Entity(
    tableName = "to_do_list_task",
    foreignKeys = [
        ForeignKey(
            entity = ToDoList::class,
            parentColumns = ["id"],
            childColumns = ["listId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["listId"], name = "task_index_listId")
    ]
)
data class ToDoListTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val listId: Int,

    val title: String,

    val color: Int = Color.TRANSPARENT,

    val favorite: Boolean = false,

    val taskCompleted: Boolean = false,

    val timeStamp: OffsetDateTime = OffsetDateTime.now(),

    val repeatMode: RepeatMode? = null,

    val weekDays: List<DayOfWeek>? = null,

    val date: LocalDate? = null,

    val reminderTime: LocalTime? = null,

    val tracker: TrackerDetails? = null
)
