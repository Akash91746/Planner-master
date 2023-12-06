package com.example.planner.feature_taskRepeat.domain.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask
import java.time.LocalDate

@Entity(
    tableName = "repeat_task",
    foreignKeys = [
        ForeignKey(
            entity = ToDoListTask::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("taskId"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["taskId"], name = "repeat_task_index_taskId"),
        Index(value = ["taskId", "timeStamp"], unique = true, name = "unique_task_by_day_index")
    ]
)
data class RepeatTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val taskId: Int,

    val taskDone: Boolean = false,

    val timeStamp: LocalDate = LocalDate.now(),

    val trackerValueOne: Double? = null,

    val trackerValueTwo: Double? = null,
)
