package com.example.planner.feature_taskInsight.domain.models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.planner.feature_taskRepeat.domain.models.RepeatTask
import com.example.planner.feature_toDoListTask.domain.models.ToDoListTask

data class TaskWithRepeatTasks(
    @Embedded
    val task: ToDoListTask,

    @Relation(
        entity = RepeatTask::class,
        parentColumn = "id",
        entityColumn = "taskId"
    )
    val list: List<RepeatTask>,
)
