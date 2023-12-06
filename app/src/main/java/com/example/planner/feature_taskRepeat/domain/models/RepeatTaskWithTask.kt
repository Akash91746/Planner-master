package com.example.planner.feature_taskRepeat.domain.models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask

data class RepeatTaskWithTask(
    @Embedded
    val repeatTask: RepeatTask,

    @Relation(
        entity = ToDoListTask::class,
        parentColumn = "taskId",
        entityColumn = "id"
    )
    val task: ToDoListTask
)
